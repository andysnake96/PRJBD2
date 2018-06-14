package feauture1.Bean;

import java.util.HashMap;

public class InfoStarInFilamentAndRectangle {
     private String errorMessage;
     HashMap<String, Integer> starsNotInFilament;
     HashMap<String, Integer> starsInFilament;

    public InfoStarInFilamentAndRectangle() {
    }

    public InfoStarInFilamentAndRectangle(String error) {
        this.errorMessage = error;
    }

    public String getErrorMessage() {
         return errorMessage;
     }

     public void setErrorMessage(String errorMessage) {
         this.errorMessage = errorMessage;
     }

    public HashMap<String, Integer> getStarsNotInFilament() {
        return starsNotInFilament;
    }

    public void setStarsNotInFilament(HashMap<String, Integer> starsNotInFilament) {
        this.starsNotInFilament = starsNotInFilament;
    }

    public HashMap<String, Integer> getStarsInFilament() {
        return starsInFilament;
    }

    public void setStarsInFilament(HashMap<String, Integer> starsInFilament) {
        this.starsInFilament = starsInFilament;
    }

    @Override
    public String toString() {
        return "InfoStarInFilamentAndRectangle{" +
                "errorMessage='" + errorMessage + '\'' +
                ", starsNotInFilament=" + starsNotInFilament +
                ", starsInFilament=" + starsInFilament +
                '}';
    }
}
