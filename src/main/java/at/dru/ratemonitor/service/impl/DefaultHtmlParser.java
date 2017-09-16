package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.service.IHtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultHtmlParser implements IHtmlParser {

    private static final SimpleDateFormat DATE_FORMAT_RATE = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final String URL = "https://www.llb.li/de/private/anlegen/direktanlagen/devisen-und-edelmetalle/devisenkurse?iframe=1";

    @Value("${application.timeOut}")
    private int timeOut;

    @Value("${application.userAgent}")
    private String userAgent;

    @Value("${application.country}")
    private String country;

    @Nonnull
    @Override
    public List<ConversionRate> getConversionRates() {
        try {
            return loadRatesFromURL();
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Cannot parse rates", e);
        }
    }

    @Nonnull
    private List<ConversionRate> loadRatesFromURL() throws IOException, ParseException {
        Document doc = Jsoup.connect(URL)
                .timeout(timeOut)
                .userAgent(userAgent)
                .get();

        ConversionRate cr = new ConversionRate();

        String changedDatePart = Optional.ofNullable(doc.select("body > div > div > p:nth-child(1)"))
                .map(Elements::text)
                .map(text -> text.replace("Datum: ", "").trim())
                .orElseThrow(() -> new IllegalStateException("Cannot select changed date"));

        String changedTimePart = Optional.ofNullable(doc.select("body > div > div > p:nth-child(2)"))
                .map(Elements::text)
                .map(text -> text.replace("Zeit: ", "").trim())
                .orElseThrow(() -> new IllegalStateException("Cannot select changed time"));

        String changedDate = changedDatePart + " " + changedTimePart;

        double buyRate = Optional.ofNullable(doc.select("body > div > div > table > tbody > tr:nth-child(6) > td:nth-child(3)"))
                .map(Elements::text)
                .map(Double::valueOf)
                .orElseThrow(() -> new IllegalStateException("Cannot select buy rate"));

        double sellRate = Optional.ofNullable(doc.select("body > div > div > table > tbody > tr:nth-child(6) > td:nth-child(4)"))
                .map(Elements::text)
                .map(Double::valueOf)
                .orElseThrow(() -> new IllegalStateException("Cannot select selll rate"));

        cr.setFromCurrency("CHF");
        cr.setCountry(country);
        cr.setToCurrency("EUR");
        cr.setBuyRate(buyRate);
        cr.setSellRate(sellRate);
        cr.setChangedDate(changedDate);
        cr.setParsedDate(DATE_FORMAT_RATE.parse(changedDate));

        return Collections.singletonList(cr);
    }

}
