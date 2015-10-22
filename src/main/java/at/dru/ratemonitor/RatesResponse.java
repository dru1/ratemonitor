package at.dru.ratemonitor;

import java.util.List;

public class RatesResponse {

    private List<ConversionRate> rates;

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<ConversionRate> getRates() {
        return rates;
    }

    public void setRates(List<ConversionRate> rates) {
        this.rates = rates;
    }
}
