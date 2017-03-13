package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman on 13-Mar-17.
 */

public class TransactionMeta implements Parcelable {
    private String currency;
    private double originalAmount;
    private double convertedAmount;

    public TransactionMeta(double originalAmount, String currency) {
        this.originalAmount = originalAmount;
        this.currency = currency;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    private TransactionMeta(Parcel in) {
        originalAmount = in.readDouble();
        currency = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(originalAmount);
        dest.writeString(currency);
    }

    private void readFromParcel(Parcel in) {
        originalAmount = in.readDouble();
        currency = in.readString();
    }

    public static final Creator<TransactionMeta> CREATOR = new Creator<TransactionMeta>() {
        @Override
        public TransactionMeta createFromParcel(Parcel in) {
            return new TransactionMeta(in);
        }

        @Override
        public TransactionMeta[] newArray(int size) {
            return new TransactionMeta[size];
        }
    };

}
