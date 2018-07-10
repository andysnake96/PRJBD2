package GUI;
import BOUNDARY.UserLogin;
import GUI.LogController;
import GUI.ViewSwap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MainMenuController {

    @FXML
    private Button RetrayInfo;

    @FXML
    private Button addSatellite;

    @FXML
    private Button recordUser;

    @FXML
    private Text text;

    @FXML
    private Button loggout;

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
    protected void distByOutline(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.DISTBYOUTLINE);
    }


    public void searchFilamentByNSeg(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.SEARCHFILAMENTBYNSEG);
    }

    @FXML
    protected void loggout(ActionEvent event) throws Exception {
        //todo destroy possible boundaries
        //...
        //swap view (fxml)
        LogController.userLogin.loggout();
        ViewSwap.getIstance().swap(event, ViewSwap.LOGGIN);
    }

    @FXML
    void RF6(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.RF6);
    }

    @FXML
    void RF8(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.RF8);
    }

    @FXML
    void importCSVs(ActionEvent event) {
        if(!LogController.userLogin.getType().equals(UserLogin.TYPEADMIN))
            this.text.setText("AZIONE NON PERMESSA ALL'UTENTE NORMALE");
        Initializable importer=new Import();
        try {
            ViewSwap.getIstance().swap(event,ViewSwap.IMPORT,importer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RF9(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.RF9);
    }
    @FXML
    void RF12(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.RF12);
    }

}
