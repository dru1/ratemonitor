package at.dru.ratemonitor;


import at.dru.ratemonitor.service.IRateProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@Configuration
@ComponentScan
@EnableScheduling
@EnableTransactionManagement
@EnableAutoConfiguration
public class Application {

	private static final Log logger = LogFactory.getLog(Application.class);
	
	@Autowired
	private IRateProvider rateUpdater;

	private final Date startup;

	public Application() {
		startup = new Date();
		logger.info("Rate Monitor created");
	}

	public Date getStartup() {
		return startup;
	}

	/**
	 * simple run!
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * update every 5 minutes
     */
	@Scheduled(fixedRateString = "${application.updateCheck}")
	public void readRatesFromWebSite() {
    	rateUpdater.update();
	}
    
}
