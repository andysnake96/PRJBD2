package BEAN;

import java.util.HashMap;

public class BeanRF9 {
    private HashMap<String,Integer> counters;
    private String errorMessage;

    public BeanRF9(HashMap<String, Integer> counters) {
        this.counters = counters;
    }

    public BeanRF9(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BeanRF9{" +
                "counters=" + counters +
                '}';
    }
}
