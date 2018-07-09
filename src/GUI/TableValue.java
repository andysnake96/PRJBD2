package GUI;

public class TableValue {
    String id, name, ell, con, flux, temp, dens ,nSeg, nameStr;

    public TableValue(String id, String name, String ell, String con, String flux, String temp, String dens, String nSeg, String nameStr) {
        this.id = id;
        this.name = name;
        this.ell = ell;
        this.con = con;
        this.flux = flux;
        this.temp = temp;
        this.dens = dens;
        this.nSeg = nSeg;
        this.nameStr = nameStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEll() {
        return ell;
    }

    public void setEll(String ell) {
        this.ell = ell;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getFlux() {
        return flux;
    }

    public void setFlux(String flux) {
        this.flux = flux;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDens() {
        return dens;
    }

    public void setDens(String dens) {
        this.dens = dens;
    }

    public String getnSeg() {
        return nSeg;
    }

    public void setnSeg(String nSeg) {
        this.nSeg = nSeg;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }
}
