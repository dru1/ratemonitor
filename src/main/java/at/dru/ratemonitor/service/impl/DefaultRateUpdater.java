package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.ConversionRateRepository;
import at.dru.ratemonitor.service.IHtmlParser;
import at.dru.ratemonitor.service.IRateUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DefaultRateUpdater implements IRateUpdater {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRateUpdater.class);

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Autowired
    private IHtmlParser htmlParser;

    private final AtomicInteger updateCounter = new AtomicInteger();
    private final AtomicReference<ZonedDateTime> lastUpdate = new AtomicReference<>();

    @Override
    public int getUpdateCounter() {
        return updateCounter.get();
    }

    @Nullable
    @Override
    public ZonedDateTime getLastUpdate() {
        return lastUpdate.get();
    }

    @Override
    public void update() {
        logger.info("Updating...");
        htmlParser.getConversionRates().forEach(this::saveConversionRate);
        logger.info("Done...");
        updateCounter.incrementAndGet();
        lastUpdate.set(ZonedDateTime.now());
    }

    private boolean isNewRate(String changedDate) {
        return conversionRateRepository.findByChangedDate(changedDate).isEmpty();
    }

    @Transactional
    private void saveConversionRate(ConversionRate conversionRate) {
        if (conversionRate.getChangedDate() != null && isNewRate(conversionRate.getChangedDate())) {
            conversionRateRepository.save(conversionRate);
        }
    }

}
