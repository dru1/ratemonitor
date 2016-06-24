package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;

public interface IRateProvider {
	
	/**
	 * @param limit Max. Anzahl
	 * @return all known rates, sorted by time
	 */
	Iterable<ConversionRate> getRates(Integer limit);

}
