package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.ConversionRateRepository;
import at.dru.ratemonitor.service.IRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DefaultRateProvider implements IRateProvider {

    private static final Sort SORT_RATE = new Sort(Sort.Direction.DESC, "id");

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Override
    public Iterable<ConversionRate> getRates(Integer limit) {
        if (limit != null) {
            PageRequest pageRequest = new PageRequest(0, limit, SORT_RATE);
            return conversionRateRepository.findAll(pageRequest);
        }
        return conversionRateRepository.findAll(SORT_RATE);
    }
}
