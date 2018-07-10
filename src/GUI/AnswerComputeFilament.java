package GUI;

import BEAN.InfoFilament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AnswerComputeFilament implements  Initializable{

    @FXML // fx:id="centroide"
    private Text centroide; // Value injected by FXMLLoader

    @FXML // fx:id="estLon"
    private Text estLon; // Value injected by FXMLLoader

    @FXML // fx:id="estLat"
    private Text estLat; // Value injected by FXMLLoader

    @FXML // fx:id="nSeg"
    private Text nSeg; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private Text info; // Value injected by FXMLLoader

    private InfoFilament infoFilament;

    public AnswerComputeFilament(InfoFilament infoFilament) {
        this.infoFilament = infoFilament;
    }

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

    @FXML
    public void initialize() {
        if(infoFilament.getErrorMessage() != null) {
            this.info.setText( infoFilament.getErrorMessage());
            return;
        }
        this.centroide.setText("Latitudine: " + String.valueOf(infoFilament.getGlatCentroide())+ " Longitudine: "+String.valueOf( infoFilament.getGlonCentroide()));
        this.estLat.setText(String.valueOf( infoFilament.getDistLat()));
        this.estLon.setText(String.valueOf( infoFilament.getDistLon()));
        this.nSeg.setText(String.valueOf(infoFilament.getnSeg()));
    }
}
