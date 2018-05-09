package ENTITY;

public class Star {
    private int id;
    private String name;
    private double glon;
    private double glat;
    private double flux;
    private String type;
    private Satellite satellite;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGlon() {
        return glon;
    }

    public double getGlat() {
        return glat;
    }

    public double getFlux() {
        return flux;
    }

    public String getType() {
        return type;
    }

    public Satellite getSatellite() {
        return satellite;
    }
}
