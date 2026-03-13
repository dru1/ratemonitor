package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;

import java.util.List;

public interface IHtmlParser {

    /**
     * @return parse conversion rates
     */
    List<ConversionRate> getConversionRates();

}
