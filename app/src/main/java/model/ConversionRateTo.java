package model;

/**
 * Created by Roman on 13-Mar-17.
 */

public class ConversionRateTo {
    private String currencyTarget;
    private double rate;

    public ConversionRateTo(String currencyTarget, double rate) {
        this.currencyTarget = currencyTarget;
        this.rate = rate;
    }

    public String getCurrencyTarget() {
        return currencyTarget;
    }

    public double getRate() {
        return rate;
    }
}