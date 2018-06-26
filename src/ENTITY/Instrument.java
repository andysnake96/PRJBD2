package ENTITY;

import java.util.List;

public class Instrument {
    private String name;
    private List<Integer> band;

    public Instrument() {
    }

    public Instrument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getBand() {
        return band;
    }

    public void setBand(List<Integer> band) {
        this.band = band;
    }
}
