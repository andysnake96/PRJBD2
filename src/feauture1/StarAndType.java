package feauture1;

/*
in questa classe ci sono la lista delle stelle in un rettangolo e il numero di stelle per ogni tipo
 */

import ENTITY.Star;

import java.util.HashMap;
import java.util.List;

public class StarAndType {
    private List<Star> stars;
    private HashMap<String, Integer> counters;

    public StarAndType(List<Star> stars, HashMap<String, Integer> counters) {
        this.stars = stars;
        this.counters = counters;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public HashMap<String, Integer> getCounters() {
        return counters;
    }

    public void setCounters(HashMap<String, Integer> counters) {
        this.counters = counters;
    }
}
