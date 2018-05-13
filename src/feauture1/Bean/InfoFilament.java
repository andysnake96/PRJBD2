package feauture1.Bean;

public class InfoFilament {
    private double glon;
    private double glat;
    private int nSeg;
    private double distLat;
    private double distLon;
    private String message;

    public InfoFilament() {
        this.message = null;
    }

    public void setGlon(double glon) {
        this.glon = glon;
    }

    public void setGlat(double glat) {
        this.glat = glat;
    }

    public void setnSeg(int nSeg) {
        this.nSeg = nSeg;
    }

    public void setDistLat(double distLat) {
        this.distLat = distLat;
    }

    public void setDistLon(double distLon) {
        this.distLon = distLon;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InfoFilament{" +
                "glon=" + glon +
                ", glat=" + glat +
                ", nSeg=" + nSeg +
                ", distLat=" + distLat +
                ", distLon=" + distLon +
                ", message='" + message + '\'' +
                '}';
    }
}
