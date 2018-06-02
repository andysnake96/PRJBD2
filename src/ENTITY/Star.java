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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGlon(double glon) {
        this.glon = glon;
    }

    public void setGlat(double glat) {
        this.glat = glat;
    }

    public void setFlux(double flux) {
        this.flux = flux;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }
}
