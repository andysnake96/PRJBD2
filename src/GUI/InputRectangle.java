package GUI;

import BOUNDARY.UserRecorded;
import BEAN.InfoStarInFilamentAndRectangle;
import BEAN.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.util.*;

public class InputRectangle {

    @FXML // fx:id="centroideLon"
    private TextField centroideLon; // Value injected by FXMLLoader

    @FXML
    private Button okButton;

    @FXML // fx:id="l"
    private TextField l; // Value injected by FXMLLoader

    @FXML // fx:id="h"
    private TextField h; // Value injected by FXMLLoader

    @FXML // fx:id="info"
    private TextArea info; // Value injected by FXMLLoader

    @FXML // fx:id="centroideLat"
    private TextField centroideLat; // Value injected by FXMLLoader





    private Integer nStarInFilament;
    private Integer nStarNotInFilament;

    @FXML
    void BackToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.MENU);
    }

    @FXML
    void execute(ActionEvent event) throws Exception {
       this.info.clear();
        this.nStarInFilament=0;
        this.nStarNotInFilament=0;
        String centroideLatText = this.centroideLat.getText();
        String centroideLonText = this.centroideLon.getText();
        String lText = this.l.getText();
        String hText = this.h.getText();
        if(centroideLatText.isEmpty() || lText.isEmpty() || hText.isEmpty() || centroideLonText.isEmpty()) {

            this.info.setText("Inserire tutte le informazioni per il rettangolo");
            return;

        }
        double centroideLat = 0, centroideLon = 0, l=0, h=0;
        try {
            centroideLat = Double.parseDouble(centroideLatText);
            centroideLon = Double.parseDouble(centroideLonText);
            l = Double.parseDouble(lText);
            h = Double.parseDouble(hText);
            if(l <= 0 || h <= 0)
                throw new NumberFormatException();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            this.info.setText("Inserire valori validi ");
        }
        UserRecorded userRecorded = LogController.userLogin.getUserRecorded();
        Rectangle rectangle = new Rectangle();
        rectangle.setGlat(centroideLat);
        rectangle.setGlon(centroideLon);
        rectangle.setH(h);
        rectangle.setL(l);
        InfoStarInFilamentAndRectangle i= userRecorded.starInFormationInRectangle(rectangle);
        if(i.getErrorMessage() != null) {

            this.info.setText(i.getErrorMessage());
            return;
        }
        visualizePerTot( i);
        visualizePerForType( i);
        this.info.setVisible(true);
    }

    private void visualizePerForType(InfoStarInFilamentAndRectangle i) {
       if(nStarInFilament >0) {
           HashMap<String, Integer> c = i.getStarsInFilament();
           Set list = c.keySet();
           Iterator<String> iter = list.iterator();
           while (iter.hasNext()) {
               String key = iter.next();
               Integer value = c.get(key);
               float perType = value * 100f / (nStarInFilament);
               this.info.appendText("Percentuale di stelle " + key + " in formazione in un filamento: " + perType + "\n");
           }
       }
        if(nStarNotInFilament > 0) {
            HashMap<String, Integer> c2 = i.getStarsNotInFilament();
            Set list2 = c2.keySet();
            Iterator<String> iter2 = list2.iterator();
            while (iter2.hasNext()) {
                String key2 = iter2.next();
                Integer value2 = c2.get(key2);
                float perType2 = value2 * 100f / (nStarNotInFilament);
                this.info.appendText("Percentuale di stelle " + key2 + " non in formazione in un filamento: " + perType2 + "\n");
            }
        }
    }

    private void visualizePerTot(InfoStarInFilamentAndRectangle i) {

        Collection<Integer> valuesInFilament =  i.getStarsInFilament().values();
        for(Integer t: valuesInFilament) {
            nStarInFilament += t;
        }
        Collection<Integer> valuesNotInFilament = i.getStarsNotInFilament().values();
        for(Integer j: valuesNotInFilament) {
            nStarNotInFilament += j;
        }
        Integer totStar = nStarInFilament+nStarNotInFilament;
        if(totStar == 0) {
            this.info.setText("Nessuna stella all'interno della regione");
            return;
        }
        float perInFilament = nStarInFilament*100f/totStar;
        this.info.appendText("Percentuale di stelle nel rettangolo e in formazione in un filamento: " +perInFilament+"\n");
        float perNotInFilament = nStarNotInFilament*100f/totStar;
        this.info.appendText("Percentuale di stelle nel rettangolo non in formazione in un filamento: " + perNotInFilament+"\n");
        
        
        
    }




}