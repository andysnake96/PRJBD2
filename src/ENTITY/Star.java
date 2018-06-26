package ENTITY;

public class Star {
    private int id;
    private String name;
    private double glon;
    private double glat;
    private double flux;
    private String type;
    private Satellite satellite;

    public Star() {}

    public Star(int id, String name, double glon, double glat, double flux, String type, Satellite satellite) {
        this.id = id;
        this.name = name;
        this.glon = glon;
        this.glat = glat;
        this.flux = flux;
        this.type = type;
        this.satellite = satellite;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Star star = (Star) o;

        if (id != star.id) return false;
        if (Double.compare(star.glon, glon) != 0) return false;
        if (Double.compare(star.glat, glat) != 0) return false;
        if (Double.compare(star.flux, flux) != 0) return false;
        if (name != null ? !name.equals(star.name) : star.name != null) return false;
        if (type != null ? !type.equals(star.type) : star.type != null) return false;
        return satellite != null ? satellite.equals(star.satellite) : star.satellite == null;
    }


}
