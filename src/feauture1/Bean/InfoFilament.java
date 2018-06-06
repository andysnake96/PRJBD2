package feauture1.Bean;

public class InfoFilament {  //questa class econtiene tutte le informazioni del filamente per l'utente

    private int id;    //informazioni del filamento
    private String name;
    private Double ellipticity;
    private Double contrast;
    private Double fluxTot;
    private Double tempAvg;
    private Double densAvg;
    private int nSeg;
    private String nameStr;

    private double distLat;  //usati per il requisito 5
    private double distLon;

    private double glonCentroide; //cordinate centroide, requisito 5
    private double glatCentroide;

    private  double distVertxUpper;  //distanza dai vertici di un filamento dal controno, requisito 11
    private  double distVertxLower;


    private String errorMessage;  // se è uguale null tutto apposto, altrimenti messaggio di errore per l'utente

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEllipticity(Double ellipticity) {
        this.ellipticity = ellipticity;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }

    public void setFluxTot(Double fluxTot) {
        this.fluxTot = fluxTot;
    }

    public void setTempAvg(Double tempAvg) {
        this.tempAvg = tempAvg;
    }

    public void setDensAvg(Double densAvg) {
        this.densAvg = densAvg;
    }

    public void setnSeg(int nSeg) {
        this.nSeg = nSeg;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getEllipticity() {
        return ellipticity;
    }

    public Double getContrast() {
        return contrast;
    }

    public Double getFluxTot() {
        return fluxTot;
    }

    public Double getTempAvg() {
        return tempAvg;
    }

    public Double getDensAvg() {
        return densAvg;
    }

    public int getnSeg() {
        return nSeg;
    }

    public String getNameStr() {
        return nameStr;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public double getDistLat() {
        return distLat;
    }

    public void setDistLat(double distLat) {
        this.distLat = distLat;
    }

    public double getDistLon() {
        return distLon;
    }

    public void setDistLon(double distLon) {
        this.distLon = distLon;
    }

    public double getGlonCentroide() {
        return glonCentroide;
    }

    public void setGlonCentroide(double glonCentroide) {
        this.glonCentroide = glonCentroide;
    }

    public double getGlatCentroide() {
        return glatCentroide;
    }

    public void setGlatCentroide(double glatCentroide) {
        this.glatCentroide = glatCentroide;
    }

    public double getDistVertxUpper() {
        return distVertxUpper;
    }

    public void setDistVertxUpper(double distVertxUpper) {
        this.distVertxUpper = distVertxUpper;
    }

    public double getDistVertxLower() {
        return distVertxLower;
    }

    public void setDistVertxLower(double distVertxLower) {
        this.distVertxLower = distVertxLower;
    }

    @Override
    public String toString() {
        return "InfoFilament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ellipticity=" + ellipticity +
                ", contrast=" + contrast +
                ", fluxTot=" + fluxTot +
                ", tempAvg=" + tempAvg +
                ", densAvg=" + densAvg +
                ", nSeg=" + nSeg +
                ", nameStr='" + nameStr + '\'' +
                ", distLat=" + distLat +
                ", distLon=" + distLon +
                ", glonCentroide=" + glonCentroide +
                ", glatCentroide=" + glatCentroide +
                ", distVertxUpper=" + distVertxUpper +
                ", distVertxLower=" + distVertxLower +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

