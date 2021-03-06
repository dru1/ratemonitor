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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class DefaultHtmlParser implements IHtmlParser {

    private static final DateTimeFormatter DATE_FORMAT_RATE = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);

    private static final String URL = "https://www.llb.li/de/private/anlegen/direktanlagen/devisen-und-edelmetalle/devisenkurse?iframe=1";

    @Value("${application.timeOut}")
    private int timeOut;

    @Value("${application.userAgent}")
    private String userAgent;

    @Nonnull
    @Override
    public List<ConversionRate> getConversionRates() {
        try {
            return loadRatesFromURL();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse rates from url.", e);
        }
    }

    @Nonnull
    private List<ConversionRate> loadRatesFromURL() throws IOException {
        Document doc = Jsoup.connect(URL)
                .timeout(timeOut)
                .userAgent(userAgent)
                .get();

        String changedDatePart = Optional.ofNullable(doc.select("body > div > div > p:nth-child(1)"))
                .map(Elements::text)
                .map(text -> text.replace("Datum: ", "").trim())
                .orElseThrow(() -> new IllegalStateException("Cannot select changed date"));

        String changedTimePart = Optional.ofNullable(doc.select("body > div > div > p:nth-child(2)"))
                .map(Elements::text)
                .map(text -> text.replace("Zeit: ", "").trim())
                .orElseThrow(() -> new IllegalStateException("Cannot select changed time"));

        String changedDate = changedDatePart + " " + changedTimePart;
        LocalDateTime parsedDate = LocalDateTime.parse(changedDate, DATE_FORMAT_RATE);

        String country = Optional.ofNullable(doc.select("body > div > div > table > tbody > tr:nth-child(8) > td:nth-child(1)"))
                .map(Elements::text)
                .orElseThrow(() -> new IllegalStateException("Cannot select country"));

        double buyRate = Optional.ofNullable(doc.select("body > div > div > table > tbody > tr:nth-child(8) > td:nth-child(3)"))
                .map(Elements::text)
                .map(Double::valueOf)
                .orElseThrow(() -> new IllegalStateException("Cannot select buy rate"));

        double sellRate = Optional.ofNullable(doc.select("body > div > div > table > tbody > tr:nth-child(8) > td:nth-child(4)"))
                .map(Elements::text)
                .map(Double::valueOf)
                .orElseThrow(() -> new IllegalStateException("Cannot select sell rate"));

        ConversionRate conversionRate = new ConversionRate();
        conversionRate.setFromCurrency("CHF");
        conversionRate.setCountry(country);
        conversionRate.setToCurrency("EUR");
        conversionRate.setBuyRate(buyRate);
        conversionRate.setSellRate(sellRate);
        conversionRate.setChangedDate(changedDate);
        conversionRate.setParsedDate(parsedDate);

        return Collections.singletonList(conversionRate);
    }

}
