package GUI;

import BOUNDARY.UserAdministrator;
import BEAN.InstrumentBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AddInstrument {

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="bands"
    private TextArea bands; // Value injected by FXMLLoader

    @FXML // fx:id="band"
    private TextField band; // Value injected by FXMLLoader

    @FXML // fx:id="add"
    private Button add; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private Text info; // Value injected by FXMLLoader

    @FXML // fx:id="satellite"
    private TextField satellite; // Value injected by FXMLLoader

    private List<Double> insertBands = new ArrayList<>();


    @FXML
    void addBand(ActionEvent event) {
        double band;
        try {
            band = Double.parseDouble(this.band.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            this.info.setText("la banda inserita di osservazione non è valida");
            return;
        }
        this.band.clear();
        this.bands.appendText(band+"\n");
        this.insertBands.add(band);


    }

    @FXML
    void addInstrument(ActionEvent event) {
        String name = this.name.getText();
        if(name.isEmpty()) {
            this.info.setText("Nome non valido");
            return ;
        }
        String satellite = this.satellite.getText();
        if(satellite.isEmpty()) {
            this.info.setText("Inserire il satellite");
            return ;
        }
        if(this.insertBands.isEmpty()) {
            this.info.setText("Inserire le bande di osservazione");
            return;
        }

        double[] array = new double[this.insertBands.size()];
        int i = 0;
        for(Double b: this.insertBands) {
            array[i] = b.doubleValue();
            i++;
        }
        InstrumentBean bean = new InstrumentBean(name,array,satellite);
        UserAdministrator administrator = null;
        try {
            administrator = LogController.userLogin.getUserAdministrator();
        } catch (Exception e) {
            e.printStackTrace();
            this.info.setText("ERRORE");
        }
        String info = administrator.addInstrument(bean);
        this.info.setText(info);
    }

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

}
