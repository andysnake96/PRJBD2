package CONTROLLER;

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

    public static void main(String args[]) {
        ComputeFilament cf = new ComputeFilament("name");
        ComputeFilament cf2 = new ComputeFilament(55);
        System.out.println(cf.getName() +" "+ cf.getId());
        System.out.println(cf2.getId()+" "+ cf2.getName());

    }
}
