package ENTITY;

public class Star {
    private int id;
    private String name;
    private int glon;
    private int glat;
    private double flux;
    private String type;
    private Satellite satellite;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGlon() {
        return glon;
    }

    public int getGlat() {
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
