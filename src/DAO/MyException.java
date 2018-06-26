package DAO;

public class MyException extends Exception {

    public MyException(String nameBuisnessRuleViolated) {
        super(nameBuisnessRuleViolated);
    }

}
