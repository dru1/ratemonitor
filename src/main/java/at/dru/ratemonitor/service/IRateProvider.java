package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.DataViewMode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IRateProvider {

    @Nonnull
    Iterable<ConversionRate> getRates(@Nullable Integer limit, @Nonnull Integer page, @Nonnull DataViewMode mode);

}
