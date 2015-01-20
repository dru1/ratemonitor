package at.dru.ratemonitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class EURRateUpdater implements IRateProvider {

	private static final SimpleDateFormat df = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

	private final Log log = LogFactory.getLog(EURRateUpdater.class);

	@Autowired
	private ConversionRateRepository conversionRateRepository;

	@Value("${application.baseUrl}")
	private String baseUrl = "";

	@Value("${application.timeOut}")
	private int timeOut;

	@Value("${application.userAgent}")
	private String userAgent;

	private int updateCounter = 0;

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

	@Override
	public Iterable<ConversionRate> getRates() {
		return conversionRateRepository.findAll();
	}

	/**
	 * @see at.dru.ratemonitor.IRateUpdater#update()
	 */
	@Override
	public void update() {
		log.info("Updating...");

		Document doc;
		try {
			doc = Jsoup.connect(baseUrl).timeout(timeOut).userAgent(userAgent)
					.get();
			for (Element el : doc.select("table#tableData tbody tr:eq(1)")) {
				// decode thhe changed date
				String changedDate = el.child(5).text() + " " + el.child(6).text();

				if (isNewRate(changedDate)) {
					saveConversionRate(el, changedDate);
				}
			}

			setUpdateCounter(getUpdateCounter() + 1);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private boolean isNewRate(String changedDate) {
		return conversionRateRepository.findByChangedDate(changedDate).isEmpty();
	}

	@Transactional
	private void saveConversionRate(Element el, String changedDate)
			throws ParseException {

		ConversionRate cr = new ConversionRate();

		// el.child(6).text(el.child(6).text() + getUpdateCounter());

		cr.setFromCurrency("CHF");
		cr.setCountry(el.child(0).text());
		cr.setToCurrency(el.child(1).text());
		cr.setBuyRate(Double.valueOf(el.child(3).text().trim()));
		cr.setSellRate(Double.valueOf(el.child(4).text().trim()));
		cr.setChangedDate(changedDate);
		cr.setParsedDate(df.parse(cr.getChangedDate()));
		conversionRateRepository.save(cr);
	}

}
