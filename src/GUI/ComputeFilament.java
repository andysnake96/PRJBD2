package GUI;

import BOUNDARY.UserRecorded;
import feauture1.Bean.ComputeFilamentBean;
import feauture1.Bean.InfoFilament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ComputeFilament {

    @FXML // fx:id="button"
    private Button button; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="negativeTextArea"
    private Text negativeTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="nameStr"
    private TextField nameStr; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private Text info; // Value injected by FXMLLoader

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

    @FXML
    void searchById(ActionEvent event) throws Exception {
        String id = this.id.getText();
        String nameStr = this.nameStr.getText().toUpperCase();
        if(id.isEmpty() || nameStr.isEmpty()) {
            this.info.setText("Inserire id e nome strumento");
            return;
        }
        int idFil;
        try {
            idFil = Integer.parseInt(id);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            this.info.setText("L'id del filamento deve essere un numero intero!!");
            return;
        }
        ComputeFilamentBean bean = new ComputeFilamentBean(idFil, nameStr);
        UserRecorded userRecorded = null;
        try {
            userRecorded = LogController.userLogin.getUserRecorded();
        } catch (Exception e) {
            e.printStackTrace();
            this.info.setText("ERRORE");
        }
        InfoFilament infoFilament =  userRecorded.recoveryInfoFilament(bean);
        AnswerComputeFilament answer = new AnswerComputeFilament(infoFilament);
        ViewSwap.getIstance().swap(event, ViewSwap.ANSWERCOMPUTEFILAMENT, answer);
    }

    @FXML
    void searchByName(ActionEvent event) throws Exception {
        String name = this.name.getText();
        if(name.isEmpty()) {
            this.info.setText("Inserire il nome dle filamento");
            return;
        }
        ComputeFilamentBean bean = new ComputeFilamentBean(name);
        UserRecorded userRecorded = null;
        try {
            userRecorded = LogController.userLogin.getUserRecorded();
        } catch (Exception e) {
            e.printStackTrace();
            this.info.setText("ERRORE");
        }
        InfoFilament infoFilament =  userRecorded.recoveryInfoFilament(bean);
        AnswerComputeFilament answer = new AnswerComputeFilament(infoFilament);
        ViewSwap.getIstance().swap(event, ViewSwap.ANSWERCOMPUTEFILAMENT, answer);


    }

}