package at.dru.ratemonitor;

import java.util.SortedSet;

public interface IRateProvider extends IRateUpdater {
	
	/**
	 * @return all known rates, sorted by time
	 */
	SortedSet<ConversionRate> getRates();

}
