package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.ConversionRateRepository;
import at.dru.ratemonitor.data.DataViewMode;
import at.dru.ratemonitor.service.IRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Component
public class DefaultRateProvider implements IRateProvider {

    private static final Sort SORT_RATE = Sort.by(Sort.Direction.DESC, "id");

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Value("${application.country}")
    private String country;

    @Nonnull
    @Override
    public Iterable<ConversionRate> getRates(@Nullable Integer limit, @Nonnull Integer page, @Nonnull DataViewMode mode) {
        if (limit != null) {
            PageRequest pageRequest = PageRequest.of(page, limit, SORT_RATE);
            switch (mode) {
                case DAY:
                    return conversionRateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                        criteriaQuery.groupBy(root.get("parsedDate"));
                        return criteriaBuilder.equal(root.get("country"), country);
                    }, pageRequest);
                case ROW:
                default:
                    return conversionRateRepository.findByCountry(country, pageRequest);
            }
        }
        return conversionRateRepository.findAll(SORT_RATE);
    }

    @Nullable
    @Override
    public ConversionRate getCurrent() {
        return conversionRateRepository.findTopByOrderByParsedDateDesc();
    }

}
