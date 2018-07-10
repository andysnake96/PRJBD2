package BEAN;

public class ComputeFilament {
    private String name;
    private Integer id;

    public ComputeFilament(String name) {
        this.name = name;
        this.id = null;
    }

    public ComputeFilament(Integer id) {
        this.id = id;
        this.name = null;
    }



    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }




}
