package ENTITY;

public class PointSkeleton {
    private Point point;
    private int idSeg;
    private int n;
    private Double flux;
    private String type;


    public PointSkeleton(Point point, int idSeg, int n, Double flux, String type) {
        this.point = point;
        this.idSeg = idSeg;
        this.n = n;
        this.flux = flux;
        this.type = type;
    }
}
