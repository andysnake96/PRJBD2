package feauture1.Bean;

import java.time.LocalDate;

/*
classe bean per le informazioni del satellite da inserire nel DB imesse dall'utente
 */
public class SatelliteBean {
    private String name;
    private LocalDate startDate;

    private LocalDate endDate;

    public SatelliteBean() {
        this.name = null;
        this.startDate = null;
        this.endDate = null;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
