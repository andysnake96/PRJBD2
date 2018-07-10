package BEAN;

import ENTITY.Satellite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
classe bean per le informazioni del satellite da inserire nel DB imesse dall'utente
 */
public class SatelliteBean {
    private String name;
    private LocalDate startDate;

    private LocalDate endDate;
    private List<String> agencies;

    public SatelliteBean() {
        this.name = null;
        this.startDate = null;
        this.endDate = null;
        this.agencies = new ArrayList<>();
    }


    public SatelliteBean(String name, LocalDate startDate, LocalDate endDate, List<String> agencies) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.agencies = agencies;
    }

    public SatelliteBean(String nameSatellite, LocalDate startDate, LocalDate endDate) {
        this.name = nameSatellite;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<String> agencies) {
        this.agencies = agencies;
    }

    @Override
    public String toString() {
        return "SatelliteBean{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", agencies=" + agencies +
                '}';
    }
}
