package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import BOUNDARY.*;

public class LogController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField paswoord;
    @FXML
    private Text textArea;

    protected static UserLogin userLogin;
    //@FXML
    //private ComboBox<String> combo;
    @FXML
    private javafx.scene.text.Text negativeOutput;

    /*@FXML
    public void push (ActionEvent event) throws Exception {

        System.out.println("buttonPressed");
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent pane2 = FXMLLoader.load(getClass().getResource("/GUI/SpecificaCarAulaConf.fxml"));
        stage.setScene(new Scene(pane2));
        //old

        //Parent newPane = FXMLLoader.load(getClass().getResource(ViewSwap.VIEW2));
        //loaded new scene? --> new content to bind to the stage
        System.out.println("pushed"+"\tcurrent thread\t\t->"+Thread.currentThread().getId());
        ViewSwap.getIstance().swap( event,ViewSwap.VIEW2);
    }*/// schema base per interfacciarsi con classe swap per cambiare fxml
    @FXML
    public void loggin(ActionEvent event) throws Exception {
        /*
        System.out.println("buttonPressed");
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent pane2 = FXMLLoader.load(getClass().getResource("/GUI/SpecificaCarAulaConf.fxml"));
        stage.setScene(new Scene(pane2));
        //old
        */
        //Parent newPane = FXMLLoader.load(getClass().getResource(ViewSwap.VIEW2));
        //loaded new scene? --> new content to bind to the stage
        System.out.println("pushed" + "\tcurrent thread\t\t->" + Thread.currentThread().getId());
        String usernameEntered = (String) this.username.getCharacters().toString();
        String paswoordEntered = (String) this.paswoord.getCharacters().toString();
        this.paswoord.clear();                                              //clear entered characters
        System.out.println("entered..." + usernameEntered + "\t\t" + paswoordEntered);

        LogController.userLogin = new UserLogin(usernameEntered,paswoordEntered);
        if(userLogin.loggin()){
            //logged=>menu
            ViewSwap.getIstance().swap(event,ViewSwap.MENU);
        }
         else {
            this.textArea.setText("LOGGIN ERRATO \n RIPROVARE...");
        }
        //TODO ADD NOT FOUND USER...=>CALL WEAP DISPLAYNOTFO
    }

    private void displayNotFoundUser() {
        this.negativeOutput.setFont(new Font(22));
        this.negativeOutput.setText("error");
    }

}