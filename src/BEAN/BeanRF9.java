package BEAN;

import ENTITY.Star;

import java.util.HashMap;
import java.util.List;

public class BeanRF9 {
    private HashMap<String,Integer> counters;
    private String errorMessage;
    private List<Star> starsInFilament;

    public BeanRF9(HashMap<String, Integer> counters, List<Star> starsInFilament) {

        this.counters = counters;
        this.starsInFilament = starsInFilament;
    }

    public BeanRF9(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HashMap<String, Integer> getCounters() {
        return counters;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Star> getStarsInFilament() {
        return starsInFilament;
    }

    @Override
    public String toString() {
        return "BeanRF9{" +
                "counters=" + counters +
                '}';
    }
}
