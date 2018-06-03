package feauture1.Bean;

public class BeanFilament {  //questa class econtiene tutte le informazioni del filamente per l'utente

    private int id;

    private String name;

    private Double ellipticity;

    private Double contrast;

    private Double fluxTot;

    private Double tempAvg;

    private Double densAvg;

    private int nSeg;

    private String nameStr;
    private String errorMessage;  // se è uguale null tutto apposto

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

    @Override
    public String toString() {
        return "BeanFilament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ellipticity=" + ellipticity +
                ", contrast=" + contrast +
                ", fluxTot=" + fluxTot +
                ", tempAvg=" + tempAvg +
                ", densAvg=" + densAvg +
                ", nSeg=" + nSeg +
                ", nameStr='" + nameStr + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

