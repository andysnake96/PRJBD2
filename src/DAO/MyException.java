package DAO;

public class MyException extends Exception {

    public MyException() {
        super("contraint check violates\n");
    }

}
