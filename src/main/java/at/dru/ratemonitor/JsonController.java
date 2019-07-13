package at.dru.ratemonitor;

import at.dru.ratemonitor.data.ConversionRate;
import at.dru.ratemonitor.data.DataViewMode;
import at.dru.ratemonitor.service.IRateProvider;
import at.dru.ratemonitor.service.IRateUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class JsonController {

    @Autowired
    private IRateProvider rateProvider;

    @Autowired
    private IRateUpdater rateUpdater;

    /**
     * @return status of the updater
     */
    @Nonnull
    @RequestMapping(value = "/json/status", method = RequestMethod.GET)
    public StatusResponse getStatus() {
        StatusResponse response = new StatusResponse();

        response.setUpdates(rateUpdater.getUpdateCounter());
        String lastUpdateAsString = Optional.ofNullable(rateUpdater.getLastUpdate())
                .map(lastUpdate -> DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(lastUpdate))
                .orElse(null);
        response.setLastUpdate(lastUpdateAsString);

        return response;
    }

    @Nonnull
    @RequestMapping(value = "/json/rates", method = RequestMethod.GET)
    public RatesResponse getRates(@RequestParam(value = "mode", defaultValue = "ROW") DataViewMode mode,
                                  @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        RatesResponse response = new RatesResponse();

        List<ConversionRate> rateList = new ArrayList<>();
        for (ConversionRate rate : rateProvider.getRates(limit, page, mode)) {
            rateList.add(rate);
        }
        response.setRates(rateList);
        response.setAmount(rateList.size());

        return response;
    }

    @Nullable
    @RequestMapping(value = "json/current", method = RequestMethod.GET)
    public ConversionRate getCurrent() {
        return rateProvider.getCurrent();
    }

}
