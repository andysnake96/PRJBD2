package GUI;

import BEAN.BeanRF9;
import BOUNDARY.UserRecorded;
import BEAN.ComputeFilamentBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class RF9 {


    @FXML // fx:id="button"
    private Button button; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="negativeTextArea"
    private TextArea info; // Value injected by FXMLLoader

    @FXML // fx:id="nameStr"
    private TextField nameStr; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

    @FXML
    void searchById(ActionEvent event) throws Exception {
        this.info.setText("");
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
        String msgOut=calculateOut(bean,userRecorded);
            this.info.setText(msgOut);
        }

    private String calculateOut(ComputeFilamentBean bean,UserRecorded boundary) {
        BeanRF9 beanRF9= boundary.starsInFilament(bean);
        if(beanRF9.getErrorMessage() != null) {
            return beanRF9.getErrorMessage();
        }
        String out= "Numero stelle contenute"+beanRF9.getStarsInFilament().size();
        Collection<Integer> valuesInFilament =  beanRF9.getCounters().values();
        Integer nStarInFilament=0;

        for(Integer t: valuesInFilament) {
            nStarInFilament += t;
        }

        System.out.println(nStarInFilament==beanRF9.getStarsInFilament().size());
        if(nStarInFilament == 0) {
            return  "Nessuna stella all'interno della regione";

        }

        HashMap<String, Integer> c = beanRF9.getCounters();
        Set list = c.keySet();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Integer value = c.get(key);
            float perType = value * 100f / (nStarInFilament);
            out+="Percentuale di stelle di tipo :" + key + "  in un filamento: " + perType + "\n" ;
        }
        return out;
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
        String outMsg=this.calculateOut(bean,userRecorded);
        this.info.setText(outMsg);


    }
}
