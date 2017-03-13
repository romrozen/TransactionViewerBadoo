package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman on 13-Mar-17.
 */

public class SkuOverview implements Parcelable {
    private String sku;
    private int numberOfTransactions;

    public SkuOverview(String sku, int numberOfTransactions) {
        this.sku = sku;
        this.numberOfTransactions = numberOfTransactions;
    }

    private SkuOverview(Parcel in) {
        sku = in.readString();
        numberOfTransactions = in.readInt();
    }


    public String getSku() {
        return sku;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sku);
        dest.writeInt(numberOfTransactions);
    }

    private void readFromParcel(Parcel in) {
        sku = in.readString();
        numberOfTransactions = in.readInt();
    }

    public static final Creator<SkuOverview> CREATOR = new Creator<SkuOverview>() {
        @Override
        public SkuOverview createFromParcel(Parcel in) {
            return new SkuOverview(in);
        }

        @Override
        public SkuOverview[] newArray(int size) {
            return new SkuOverview[size];
        }
    };


}