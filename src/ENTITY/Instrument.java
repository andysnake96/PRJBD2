package ENTITY;

import java.util.List;

public class Instrument {
    private String name;
    private List<Integer> band;

    public Instrument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
