package BEAN;

import java.util.Collection;

public class BeanRF12 {
    double glat;
    double glon;
    double flux;
    double distance;
    String starName;
    @Override
    public String toString( ) {
        return
                "glat=" + glat +
                ", glon=" + glon +
                ", flux=" + flux +
                ", distance=" + distance +
                ", starName='" + starName + '\'' +
                '}';
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public BeanRF12(double glat, double glon, double flux, double distance, String starName) {
        this.glat = glat;
        this.glon = glon;
        this.flux = flux;
        this.distance = distance;
        this.starName= starName;
    }

    public double getGlat() {
        return glat;
    }

    public void setGlat(double glat) {
        this.glat = glat;
    }

    public double getGlon() {
        return glon;
    }

    public void setGlon(double glon) {
        this.glon = glon;
    }

    public double getFlux() {
        return flux;
    }

    public void setFlux(double flux) {
        this.flux = flux;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
