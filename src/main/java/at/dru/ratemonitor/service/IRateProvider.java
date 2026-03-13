package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.DataViewMode;
import org.jspecify.annotations.Nullable;

public interface IRateProvider {

    Iterable<ConversionRate> getRates(@Nullable Integer limit, Integer page, DataViewMode mode);

    @Nullable
    ConversionRate getCurrent();
}
