package feauture1.Bean;

/*
classe bean utilizzata per trasportare le informazioni di un nuovo strumento da inserire nel DB
 */

public class InstrumentBean {
    private String name;
    private double[] band;
    private String satellite;

    public String getName() {
        return name;
    }

    public double[] getBand() {
        return band;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBand(double[] band) {
        this.band = band;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getBandString() {
        String band = "{";
        int i;
        for( i = 0; i < this.band.length-1; i++) {
            band += this.band[i] + ",";
        }
        band += this.band[i] +"}";
        return band;
    }
}
