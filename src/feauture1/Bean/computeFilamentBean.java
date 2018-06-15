package feauture1.Bean;


/*
classe bean che utilizzo per trasportare infromazioni del filamento che l'utente ha scelto. L'utente può scegliere
il nome o l'id con il relativo strumento.
 */
public class computeFilamentBean {
    private String name;
    private int id;
    private String nameStr;
    private int type;  // se 0 ho il nome del filamento se è 1 hol'id e il nome strumento

    public computeFilamentBean(String name) {
        this.name = name;
        this.type = 0;
    }

    public computeFilamentBean(int id, String nameStr) {
        this.id = id;
        this.nameStr = nameStr;
        this.type = 1;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getNameStr() {
        return nameStr;
    }

    public int getType() {
        return type;
    }
}
