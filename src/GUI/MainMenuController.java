package GUI;

import BOUNDARY.UserLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MainMenuController {
    //logged to acces this controller
    @FXML
    Button PrenotazioneConId;
    @FXML
    Button PrenotazioneConCaratteristiche;
    @FXML
    Button loggout;
    @FXML
    Button visualizzaPrenotazioniAttive;
    @FXML
    Text text;
    //andysnake buttons
    @FXML
    Button visualizzaDisponibilita;
    @FXML
    Button visualizzaPrenotazioniAttiveTot;

    @FXML
    Button definizioneSessione;

    @FXML
    Button storicoPrenotazioni;

    @FXML
    protected void loggout(ActionEvent event) throws Exception {
        //todo destroy possible boundaries
        //...
        //swap view (fxml)
        LogController.userLogin.loggout();
        ViewSwap.getIstance().swap(event, ViewSwap.LOGGIN);
    }

    @FXML
    protected void recordUser(ActionEvent event) throws Exception {
        //todo GOTO PRENOTAZIONE CON ID CONTROLLER.... SUBTIPE OF SPECIFICACARAULACONTROLLER?
            if(LogController.userLogin.getType().equals(UserLogin.TYPEUSER))
                text.setText("AZIONE NON CONSENTITA ALL'UTENTE NORMALE");
            else {
                ViewSwap.getIstance().swap(event, ViewSwap.RECORDUSER);
            }
    }


    @FXML
    protected void addSatellite(ActionEvent event) throws Exception {
        if(LogController.userLogin.getType().equals(UserLogin.TYPEUSER))
            text.setText("AZIONE NON CONSENTITA ALL'UTENTE NORMALE");
        else {
            ViewSwap.getIstance().swap(event, ViewSwap.ADDSATELLITE);
        }
    }


    @FXML
    protected void starInRectangle(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.STARINRECTANGLE);
    }

    @FXML
    protected void addInstrument(ActionEvent event) throws Exception {
        if(LogController.userLogin.getType().equals(UserLogin.TYPEUSER))
            text.setText("AZIONE NON CONSENTITA ALL'UTENTE NORMALE");
        else {
            ViewSwap.getIstance().swap(event, ViewSwap.ADDINSTRUMENT);
        }
    }



    @FXML
    protected void retrayInfoFilament(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.COMPUTEFILAMENT);
    }

    @FXML
    protected void apriUnAnnoAccademico(ActionEvent event) throws Exception {
        }



    @FXML
    protected void distByOutline(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.DISTBYOUTLINE);
    }


    public void searchFilamentByNSeg(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.SEARCHFILAMENTBYNSEG);
    }
}
