package at.dru.ratemonitor.service;

import javax.annotation.Nullable;
import java.util.Date;

public interface IRateUpdater {

	/**
	 * @return how many updates are processed
	 */
	int getUpdateCounter();

    /**
     * @return date of the latest successful update
     */
    @Nullable
    Date getLastUpdate();

	/**
	 * invoke the update
	 */
	void update();
}