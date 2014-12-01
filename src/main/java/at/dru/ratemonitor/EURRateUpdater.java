package at.dru.ratemonitor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EURRateUpdater implements IRateProvider {

	private static final SimpleDateFormat df = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");
	
	private final Log log = LogFactory.getLog(EURRateUpdater.class);

	@Value("${application.baseUrl}")
	private String baseUrl = "";
	
	@Value("${application.timeOut}")
	private int timeOut;
	
	@Value("${application.userAgent}")
	private String userAgent;

	private int updateCounter = 0;

	private SortedSet<ConversionRate> conversionRates = Collections
			.synchronizedSortedSet(new TreeSet<ConversionRate>());

	public SortedSet<ConversionRate> getRates() {
		return conversionRates;
	}

	/**
	 * @see at.dru.ratemonitor.IRateUpdater#getUpdateCounter()
	 */
	@Override
	public synchronized int getUpdateCounter() {
		return updateCounter;
	}

	private synchronized void setUpdateCounter(int updateCounter) {
		this.updateCounter = updateCounter;
	}

	/**
	 * @see at.dru.ratemonitor.IRateUpdater#update()
	 */
	@Override
	public void update() {
		log.info("Updating...");
		
		Document doc;
		try {
			doc = Jsoup.connect(baseUrl).timeout(timeOut).userAgent(userAgent).get();
			for (Element el : doc.select("table#tableData tbody tr:eq(1)")) {
				ConversionRate cr = new ConversionRate();

				// el.child(6).text(el.child(6).text() + getUpdateCounter());

				cr.from = "CHF";
				cr.country = el.child(0).text();
				cr.to = el.child(1).text();
				cr.buyRate = Double.valueOf(el.child(3).text().trim());
				cr.sellRate = Double.valueOf(el.child(4).text().trim());
				cr.date = el.child(5).text() + " " + el.child(6).text();
				cr.parsedDate = df.parse(cr.date);
				conversionRates.add(cr);
			}

			setUpdateCounter(getUpdateCounter() + 1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
