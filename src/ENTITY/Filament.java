package ENTITY;


import java.util.List;

public class Filament {
    private Outline outline;
    private List<PointSkeleton> pointSkeletonBranch;
    private List<PointSkeleton> pointSkeletonSpine;

    private int id;

    private String name;

    private Double ellipticity;

    private Double contrast;

    private Double fluxTot;

    private Double tempAvg;

    private Double densAvg;

    private int nSeg;

    private Instrument instrument;

    public Outline getOutline() {
        return outline;
    }
    public Filament(){};

    public Filament(int id, String nameInstrument) {
        this.id = id;
        this.instrument = new Instrument(nameInstrument);
    }

    public Filament(int id, String name, Double ellipticity, Double contrast, Double fluxTot, Double tempAvg, Double densAvg, int nSeg, String instrumentName) {
        this.id = id;
        this.name = name;
        this.ellipticity = ellipticity;
        this.contrast = contrast;
        this.fluxTot = fluxTot;
        this.tempAvg = tempAvg;
        this.densAvg = densAvg;
        this.nSeg = nSeg;
        this.instrument = new Instrument(instrumentName);
    }

    public void setOutline(Outline outline) {
        this.outline = outline;
    }

    public List<PointSkeleton> getPointSkeletonBranch() {
        return pointSkeletonBranch;
    }

    public void setPointSkeletonBranch(List<PointSkeleton> pointSkeletonBranch) {
        this.pointSkeletonBranch = pointSkeletonBranch;
    }

    public List<PointSkeleton> getPointSkeletonSpine() {
        return pointSkeletonSpine;
    }

    public void setPointSkeletonSpine(List<PointSkeleton> pointSkeletonSpine) {
        this.pointSkeletonSpine = pointSkeletonSpine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getEllipticity() {
        return ellipticity;
    }

    public void setEllipticity(Double ellipticity) {
        this.ellipticity = ellipticity;
    }

    public Double getContrast() {
        return contrast;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }

    public Double getFluxTot() {
        return fluxTot;
    }

    public void setFluxTot(Double fluxTot) {
        this.fluxTot = fluxTot;
    }

    public Double getTempAvg() {
        return tempAvg;
    }

    public void setTempAvg(Double tempAvg) {
        this.tempAvg = tempAvg;
    }

    public Double getDensAvg() {
        return densAvg;
    }

    public void setDensAvg(Double densAvg) {
        this.densAvg = densAvg;
    }

    public int getnSeg() {
        return nSeg;
    }

    public void setnSeg(int nSeg) {
        this.nSeg = nSeg;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public String toString() {
        return "Filament{" +
                "outline=" + outline +
                ", pointSkeletonBranch=" + pointSkeletonBranch +
                ", pointSkeletonSpine=" + pointSkeletonSpine +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", ellipticity=" + ellipticity +
                ", contrast=" + contrast +
                ", fluxTot=" + fluxTot +
                ", tempAvg=" + tempAvg +
                ", densAvg=" + densAvg +
                ", nSeg=" + nSeg +
                ", instrument=" + instrument +
                '}';
    }
}

/*
    public Outline getOutline() {
        return outline;
    }

    public List<PointSkeleton> getPointSkeletonBranch() {
        return pointSkeletonBranch;
    }

    public List<PointSkeleton> getPointSkeletonSpine() {
        return pointSkeletonSpine;
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

    public Instrument getInstrument() {
        return instrument;
    }
}
*/