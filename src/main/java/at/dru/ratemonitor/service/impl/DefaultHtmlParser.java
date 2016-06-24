package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.service.IHtmlParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

@Component
public class DefaultHtmlParser implements IHtmlParser {

    private static final SimpleDateFormat DATE_FORMAT_RATE = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private static final String LLB_QUOTES_URL = "http://quotes.llb.li/llbExchangeRateNoten.html";
    private static final Function<Document, ConversionRate> LLB_QUOTES_MAPPER = (doc) -> {
        ConversionRate cr = new ConversionRate();

        Element el = doc.select("table#tableData tbody tr:eq(1)").first();
        if (el == null) {
            throw new IllegalStateException("Nothing selected");
        }

        // decode the changed date
        String changedDate = el.child(5).text() + " " + el.child(6).text();

        cr.setFromCurrency("CHF");
        cr.setCountry(el.child(0).text());
        cr.setToCurrency(el.child(1).text());
        cr.setBuyRate(Double.valueOf(el.child(3).text().trim()));
        cr.setSellRate(Double.valueOf(el.child(4).text().trim()));
        cr.setChangedDate(changedDate);
        try {
            cr.setParsedDate(DATE_FORMAT_RATE.parse(changedDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return cr;
    };

    private static final String LLB_DEVISEN_URL = "https://www.llb.li/de/privatkunden/anlegen-sparen/noten-devisen-edelmetallkurse/devisenkurse";
    private static final Function<Document, ConversionRate> LLB_DEVISEN_MAPPER = (doc) -> {
        ConversionRate cr = new ConversionRate();

        Element date = doc.select("#pagetype_0_pagecontent_0_ctl00_panData > div").first();
        if (date == null) {
            throw new IllegalStateException("Nothing selected");
        }

        Element data = doc.select("#pagetype_0_pagecontent_0_ctl00_panData > table > tbody > tr").get(5);
        if (data == null) {
            throw new IllegalStateException("Nothing selected");
        }

        // decode the changed date
        String changedDate = date.child(0).ownText().trim() + " " + date.child(1).ownText().trim();

        cr.setFromCurrency("CHF");
        cr.setCountry(data.child(0).text());
        cr.setToCurrency("EUR");
        cr.setBuyRate(Double.valueOf(data.child(2).text().trim()));
        cr.setSellRate(Double.valueOf(data.child(3).text().trim()));
        cr.setChangedDate(changedDate);
        try {
            cr.setParsedDate(DATE_FORMAT_RATE.parse(changedDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return cr;
    };

    @Override
    public Function<Document, ConversionRate> getMapper(String baseUrl) {
        switch (baseUrl) {
            case LLB_QUOTES_URL:
                return LLB_QUOTES_MAPPER;
            case LLB_DEVISEN_URL:
                return LLB_DEVISEN_MAPPER;
            default:
                throw new RuntimeException("Unsupported url");
        }
    }

}
