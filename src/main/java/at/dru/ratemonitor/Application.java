package at.dru.ratemonitor;

import at.dru.ratemonitor.service.IRateUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Configuration
@ComponentScan
@EnableScheduling
@EnableTransactionManagement
@EnableAutoConfiguration
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private IRateUpdater rateUpdater;

    private final ZonedDateTime startup;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public Application() {
        startup = ZonedDateTime.now();
        logger.info("Rate Monitor created at {}.", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(startup));
    }

    /**
     * update every 5 minutes
     */
    @Scheduled(fixedRateString = "${application.updateCheck}")
    public void readRatesFromWebSite() {
        rateUpdater.update();
    }

}
