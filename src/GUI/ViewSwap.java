package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ViewSwap {
    public static final String COMPUTEFILAMENT = "/GUI/fxml/InputFilament.fxml";
    public static final String STARINRECTANGLE = "/GUI/fxml/InputRectangle.fxml";
    public static final String DISTBYOUTLINE = "/GUI/fxml/InputFilament2.fxml";
    public static final String SEARCHFILAMENTBYNSEG = "/GUI/fxml/InputNSeg.fxml";
    public static final String OUTPUTNSEG = "/GUI/fxml/OutputNSeg.fxml";
    //RESPONSABILITY OF SWAP SCENE IN THE ONLY STAGE
    //singleton instance because getClass cannot be used from static context
    protected static String LOGGIN = "/GUI/fxml/loggin.fxml";
    protected static String RECORDUSER = "/GUI/fxml/RecordUser.fxml";
    protected static String RISPOSTA = "/GUI/fxml/Risposta.fxml";
    protected static String MENU = "/GUI/fxml/Menu.fxml";
    protected static String RF9  ="/GUI/fxml/RF9.fxml";
    protected static String RF6  ="/GUI/fxml/RF6.fxml";
    protected static String RF8  ="/GUI/fxml/RF8.fxml";
    protected static String RF12="/GUI/fxml/RF12.fxml";
    protected static String IMPORT="/GUI/fxml/Import.fxml";

    protected static String RISPOSTAPRENOTAZIONEATTIVE = "/GUI/fxml/PrenotazioniAttiveRisposta.fxml";
    protected static String APERTUREANNOACCADEMICO = "/GUI/fxml/InputAnnoAccademico.fxml";
    protected static String INPUTID = "/GUI/fxml/SpecificaIdAulaConf.fxml";
    protected static String VERIFICADISPONIBILITA = "/GUI/fxml/verificaDisponibilita.fxml";
    protected static String ADDINSTRUMENT = "/GUI/fxml/DataInstrument.fxml";
    protected static String DEFINIZIONESESSIONE = "/GUI/fxml/DefinizioneSessione.fxml";
    protected static String STORICOPRENOTAZIONIPROF = "/GUI/fxml/RicercaPrenotazioniProf.fxml";
    protected static String STORICOPRENOTAZIONISEGR = "/GUI/fxml/RicercaPrenotazioniSegr.fxml";
    protected static String ADDSATELLITE = "/GUI/fxml/DataSatellite.fxml";

    protected static String ANSWERCOMPUTEFILAMENT = "/GUI/fxml/AnswerComputeFilament.fxml";

    protected static ViewSwap viewSwap;

    public static ViewSwap getIstance() {
        //SINGLETON INSTANCE :)

        if (ViewSwap.viewSwap == null) {
            ViewSwap.viewSwap = new ViewSwap();
            return ViewSwap.viewSwap;
        } else {
            return ViewSwap.viewSwap;
        }
    }


    public void swap(ActionEvent event, String url) throws Exception {
        //swap scene inside stage to newly created stage taken from url
        //NB -> url have to be in static attribute of swapClass :)
        //event needed to access to main Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage -> main windows of GUI
        Parent newRoot = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(newRoot));
        //change scene in the stage=====> other controller loaded
        //DONE

    }

    public void swap(ActionEvent event, String url, Initializable controller) throws Exception {
        //version to swapWithState
        //todo nB CAST PER CONTROLLER in caller
        //swap scene inside stage to newly created stage taken from url
        //set fxml controller to instantiated controller controller (casted from caller)
        //to pass data among controllers instatiates...
        //NB -> url have to be in static attribute of swapClass :)
        //event needed to access to main Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage -> main windows of GUI
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        loader.setController(controller); //set controller to fxml from instance of controller passed
        Parent newRoot = loader.load();
        //todo controller implement initialize so every  time will be callsed initialize metod
        //this belong to swap logic... :))
        //controller.initialize();      //chiamato automaticamente vedi JAVAFX doc
        //polymporfism from interface :) xD
        stage.setScene(new Scene(newRoot));
        //change scene in the stage=====> other controller loaded
        //DONE

    }

}
