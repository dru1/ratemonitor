package at.dru.ratemonitor.service.impl;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.ConversionRateRepository;
import at.dru.ratemonitor.service.IHtmlParser;
import at.dru.ratemonitor.service.IRateUpdater;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class DefaultRateUpdater implements IRateUpdater {

    private static final Log logger = LogFactory.getLog(DefaultRateUpdater.class);

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Autowired
    private IHtmlParser htmlParser;

    @Value("${application.baseUrl}")
    private String baseUrl;

    @Value("${application.timeOut}")
    private int timeOut;

    @Value("${application.userAgent}")
    private String userAgent;

    private Collection<Document> getDocuments() {
        try {
            return Collections.singleton(getDocument(baseUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document getDocument(String baseUrl) throws IOException {
        return Jsoup.connect(baseUrl).timeout(timeOut).userAgent(userAgent).get();
    }

    private int updateCounter = 0;

    /**
     * @see IRateUpdater#getUpdateCounter()
     */
    @Override
    public synchronized int getUpdateCounter() {
        return updateCounter;
    }

    private synchronized void setUpdateCounter(int updateCounter) {
        this.updateCounter = updateCounter;
    }

    /**
     * @see IRateUpdater#update()
     */
    @Override
    public void update() {
        logger.info("Updating...");

        getDocuments().stream().map(htmlParser.getMapper(baseUrl)).forEach(this::saveConversionRate);

        setUpdateCounter(getUpdateCounter() + 1);
    }

    private boolean isNewRate(String changedDate) {
        return conversionRateRepository.findByChangedDate(changedDate).isEmpty();
    }

    @Transactional
    private void saveConversionRate(ConversionRate cr) {
        if (cr.getChangedDate() != null && isNewRate(cr.getChangedDate())) {
            conversionRateRepository.save(cr);
        }
    }

}
