package BEAN;

public class Rectangle {
    private double l; //lato del rettangolo
    private double h; //altezza del rettangolo
    private double glat; //posizione del centroide
    private double glon;



    public double getL() {
        return l;
    }

    public void setL(double l) throws Exception {
        if(l <= 0) {
            throw new Exception();
        }
        this.l = l;
    }

    public double getH()  {


        return h;
    }

    public void setH(double h) throws Exception {
        if(h <= 0) {
            throw new Exception();
        }
        this.h = h;
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
}
