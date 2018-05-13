package ENTITY;

public class Point {
    private double lat;
    private double glon;

    public double getLat() {
        return lat;
    }

    public double getGlon() {
        return glon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setGlon(double glon) {
        this.glon = glon;
    }

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", glon=" + glon +
                '}';
    }
}
