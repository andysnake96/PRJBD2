
package GUI;

import BOUNDARY.UserRecorded;
import BEAN.InfoFilament;
import BEAN.RangeNSeg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class InputNSeg {

    @FXML // fx:id="nSegMin"
    private TextField nSegMin; // Value injected by FXMLLoader

    @FXML // fx:id="nSegMax"
    private TextField nSegMax; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private Text info; // Value injected by FXMLLoader

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

    @FXML
    void execute(ActionEvent event) throws Exception {

        String nSegMinText = this.nSegMin.getText();
        String nSegMaxText = this.nSegMax.getText();
        if(nSegMaxText.isEmpty() || nSegMinText.isEmpty()) {
            this.info.setText("Inserire tutti i valori");
            return;
        }
        int nSegMin = 0, nSegMax = 0;
        try {
             nSegMin = Integer.parseInt(nSegMinText);
             nSegMax = Integer.parseInt(nSegMaxText);
            if (nSegMax - nSegMin < 3 || nSegMax < 0 || nSegMin < 0) {
                throw  new NumberFormatException();
            }

        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            this.info.setText("Intervallo non valido, inserire numeri interi positivi con distanza tra loro di almeno tre");

        }

        RangeNSeg range = new RangeNSeg(nSegMin, nSegMax);
        UserRecorded userRecorded = LogController.userLogin.getUserRecorded();
        List<InfoFilament> infoFilaments = userRecorded.searchFilamentNSeg(range);
        if(infoFilaments.get(0).getErrorMessage() != null) {
            this.info.setText(infoFilaments.get(0).getErrorMessage());
            return;
        }
        OutputNSeg outputNSeg = new OutputNSeg(infoFilaments);
        ViewSwap.getIstance().swap(event, ViewSwap.OUTPUTNSEG, outputNSeg);
    }

}