package GUI;

import BOUNDARY.UserAdministrator;
import BEAN.SatelliteBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddSatellite {

    @FXML // fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="backToMenuButton"
    private Button backToMenuButton; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private Text info; // Value injected by FXMLLoader

    @FXML // fx:id="agency"
    private TextField agency; // Value injected by FXMLLoader

    @FXML // fx:id="startDate"
    private DatePicker startDate; // Value injected by FXMLLoader

    @FXML // fx:id="endDate"
    private DatePicker endDate; // Value injected by FXMLLoader

    @FXML // fx:id="add"
    private Button add; // Value injected by FXMLLoader

    @FXML // fx:id="agencies"
    private TextArea itemsInsert; // Value injected by FXMLLoader

    private List<String> agencies = new ArrayList<>();

    @FXML
    void addAgency(ActionEvent event) {
        String agency = this.agency.getText();
        if (agency.isEmpty()) {
            info.setText("Inserire angenzia");
            return;
        }
        this.agency.clear();
        this.itemsInsert.appendText(agency+"\n");
        this.agencies.add(agency);
    }


    @FXML
    void addSatellite(ActionEvent event) {
        String name = this.name.getText();
        if (name.isEmpty()) {
            this.info.setText("Inserire nome");
            return;
        }
        LocalDate starDate = this.startDate.getValue();
        if (starDate == null || !starDate.isBefore(LocalDate.now())) {
            info.setText("Inserire una data di inizio valida");
            return;
        }
        LocalDate endDate = this.endDate.getValue();
        if(endDate != null) {
            if(!endDate.isBefore(LocalDate.now()) || !starDate.isBefore(endDate)) {
                info.setText("Inserire una data di fine valida");
                return;
            }
        }
        if (this.agencies.isEmpty()) {
            info.setText("Aggiungere almeno un'agenzia");
            return;
        }
        SatelliteBean satelliteBean = new SatelliteBean(name, starDate, endDate, this.agencies);
        UserAdministrator administrator = null;
        try {
            administrator = LogController.userLogin.getUserAdministrator();
        } catch (Exception e) {
            e.printStackTrace();
            info.setText("ERRORE");
        }
        String info = administrator.addSatellite(satelliteBean);
        this.info.setText(info);
    }



    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }
}