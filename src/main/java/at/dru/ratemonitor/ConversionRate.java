package at.dru.ratemonitor;

import java.util.Date;

public class ConversionRate implements Comparable<ConversionRate> {
	String from;

	String country;

	String to;

	double buyRate;

	double sellRate;

	String date;

	Date parsedDate;

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConversionRate
				&& date.equals(((ConversionRate) obj).date);
	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}

	@Override
	public String toString() {
		return date + "; " + country + "; " + from + " -> " + to
				+ "; buy = " + buyRate + "; sell = " + sellRate + ";";
	}

	@Override
	public int compareTo(ConversionRate o) {
		return this.parsedDate.compareTo(o.parsedDate);
	}
}