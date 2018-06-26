package feauture1.Bean;

/*
per il requisito 7, contiene l'intervallo del numero di segmenti inserito dall'utente
 */

public class RangeNSeg {

    private int nSegMin;
    private int nSegMax;

    public RangeNSeg(int nSegMin, int nSegMax) {
        this.nSegMin = nSegMin;
        this.nSegMax = nSegMax;
        //TODO controllora se i dati sono giusti o farlo nella grafica
    }

    public int getnSegMin() {
        return nSegMin;
    }

    public int getnSegMax() {
        return nSegMax;
    }
}
