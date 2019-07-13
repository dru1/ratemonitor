package at.dru.ratemonitor.service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public interface IRateUpdater {

    /**
     * @return how many updates are processed
     */
    int getUpdateCounter();

    /**
     * @return date of the latest successful update
     */
    @Nullable
    ZonedDateTime getLastUpdate();

    /**
     * invoke the update
     */
    void update();

}