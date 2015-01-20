package at.dru.ratemonitor;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ConversionRate {

	private Long id;

	private String fromCurrency;

	private String country;

	private String toCurrency;

	private double buyRate;

	private double sellRate;

	private String changedDate;

	private Date parsedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic(optional = false)
	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String from) {
		this.fromCurrency = from;
	}

	@Basic(optional = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Basic(optional = false)
	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String to) {
		this.toCurrency = to;
	}

	@Basic(optional = false)
	public double getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(double buyRate) {
		this.buyRate = buyRate;
	}

	@Basic(optional = false)
	public double getSellRate() {
		return sellRate;
	}

	public void setSellRate(double sellRate) {
		this.sellRate = sellRate;
	}

	@Basic(optional = false)
	public String getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(String date) {
		this.changedDate = date;
	}

	@Basic(optional = false)
	public Date getParsedDate() {
		return parsedDate;
	}

	public void setParsedDate(Date parsedDate) {
		this.parsedDate = parsedDate;
	}

	@Override
	public String toString() {
		return changedDate + "; " + country + "; " + fromCurrency + " -> "
				+ toCurrency + "; buy = " + buyRate + "; sell = " + sellRate
				+ ";";
	}

}