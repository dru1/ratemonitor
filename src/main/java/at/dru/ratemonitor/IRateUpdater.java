package at.dru.ratemonitor;

public interface IRateUpdater {

	/**
	 * @return how many updates are processed
	 */
	int getUpdateCounter();

	/**
	 * invoke the update
	 */
	void update();

}