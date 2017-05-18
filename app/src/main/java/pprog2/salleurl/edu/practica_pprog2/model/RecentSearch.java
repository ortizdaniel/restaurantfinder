package pprog2.salleurl.edu.practica_pprog2.model;

/**
 * Created by Mundirisa on 18/05/2017.
 */

public class RecentSearch {
    private boolean isText;
    private String searchText;
    private double latitude;
    private double longitude;
    private int radius;

    public boolean isText() {
        return isText;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
