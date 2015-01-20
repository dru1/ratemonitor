package at.dru.ratemonitor;

public interface IRateProvider extends IRateUpdater {
	
	/**
	 * @return all known rates, sorted by time
	 */
	Iterable<ConversionRate> getRates();

}
