package pprog2.salleurl.edu.practica_pprog2.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Mundirisa on 16/05/2017.
 */

public class FoodLocal implements Parcelable {
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private String address;
    private String openingHour;
    private String closingHour;
    private double rating;
    private String description;


    public FoodLocal() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected FoodLocal(Parcel in) {
        name = in.readString();
        type = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        openingHour = in.readString();
        closingHour = in.readString();
        rating = in.readDouble();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(address);
        dest.writeString(openingHour);
        dest.writeString(closingHour);
        dest.writeDouble(rating);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FoodLocal> CREATOR = new Parcelable.Creator<FoodLocal>() {
        @Override
        public FoodLocal createFromParcel(Parcel in) {
            return new FoodLocal(in);
        }

        @Override
        public FoodLocal[] newArray(int size) {
            return new FoodLocal[size];
        }
    };
}
