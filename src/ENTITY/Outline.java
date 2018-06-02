package ENTITY;


import java.util.List;

public class Outline {
    private List<Point> points;

    public Outline(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
}
