package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;

import javax.annotation.Nonnull;
import java.util.List;

public interface IHtmlParser {

    /**
     * @return parse conversion rates
     */
    @Nonnull
    List<ConversionRate> getConversionRates();

}
