package at.dru.ratemonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JsonController {

    @Autowired
    private IRateProvider rateProvider;

    @RequestMapping(value = "/json/rates", method = RequestMethod.GET)
    public RatesResponse getRates(@RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "groupBy", required = false) String groupBy) {
        RatesResponse response = new RatesResponse();

        List<ConversionRate> rateList = new ArrayList<>();
        for (ConversionRate rate : rateProvider.getRates(limit)) {
            rateList.add(rate);
        }
        response.setRates(rateList);
        response.setAmount(rateList.size());

        return response;
    }

}
