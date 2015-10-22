package at.dru.ratemonitor;

public interface IRateProvider extends IRateUpdater {
	
	/**
	 * @param limit Max. Anzahl
	 * @return all known rates, sorted by time
	 */
	Iterable<ConversionRate> getRates(Integer limit);

}
