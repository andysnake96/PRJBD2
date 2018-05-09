package ENTITY;

import java.util.Objects;
import java.lang.Exception;
public class User {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String type;

    public User(String name, String surname, String username, String password, String email, String type) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.setType(type);
    }


    private void setType(String type) {
        if(!(type.equals("User") || type.equals("Administrator"))) {
            System.err.println("error");
        }

        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
