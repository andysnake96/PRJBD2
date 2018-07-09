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
    protected void PrenotazioneConId(ActionEvent event) throws Exception {
        //todo GOTO PRENOTAZIONE CON ID CONTROLLER.... SUBTIPE OF SPECIFICACARAULACONTROLLER?
            if(LogController.userLogin.getType().equals(UserLogin.TYPEADMIN))
                text.setText("AZIONE NON CONSENTITA ALL'UTENTE NORMALE");
            ViewSwap.getIstance().swap(event,"/GUI/fxml/RF6.fxml");

    }


    @FXML
    protected void prenotazioneConCaratteristiche(ActionEvent event) throws Exception {
        }

    @FXML
    protected void visualizzaDisponibilita(ActionEvent event) throws Exception {
    }

    @FXML
    protected void visualizzaPrenotazioniAttiveToT(ActionEvent event) throws Exception {
    }

    @FXML
    protected void visualizzaPrenotazioniAttive(ActionEvent event) throws Exception {
    }

    @FXML
    protected void apriUnAnnoAccademico(ActionEvent event) throws Exception {
        }

    @FXML
    protected void visualizzaStorico(ActionEvent event) throws Exception {
    }

    @FXML
    protected void definisciSessione(ActionEvent event) throws Exception {
    }


}
