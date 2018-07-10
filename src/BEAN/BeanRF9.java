package BEAN;

import ENTITY.Filament;
import ENTITY.Star;

import java.util.HashMap;
import java.util.List;

public class BeanRF9 {
    private HashMap<String,Integer> counters;
    private String errorMessage;
    private List<Star> starsInFilament;
    private Filament filament;

    public BeanRF9() {

    }

    public void setFilament(Filament filament) {
        this.filament = filament;
    }

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

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BeanRF9{" +
                "counters=" + counters +
                ", errorMessage='" + errorMessage + '\'' +
                ", starsInFilament=" + starsInFilament +
                '}';
    }
}
