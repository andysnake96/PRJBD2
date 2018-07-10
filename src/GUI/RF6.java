package GUI;

import BOUNDARY.UserRecorded;
import BEAN.InfoFilament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

import static GUI.Pagination_base.createPage;


public class RF6 {
    @FXML
    Pagination pagination;
    @FXML
    Button buttonBK;


    @FXML
    TextField ellipticitymin;

    @FXML
    TextField ellipticitymax;
    @FXML
    TextField brightness;

    @FXML
    Label info;

    List<InfoFilament> infoFilaments;

    public void initialize(){
    }
    @FXML
    protected void back(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event,ViewSwap.MENU);

    }

    @FXML
    protected void search() {
        //getting input values
        if(infoFilaments!=null)
            infoFilaments.clear();
        this.info.setText("");  //clear prev
        double ellMax;
        double ellMin;
        double bright;
        try{
             ellMax= Double.parseDouble(this.ellipticitymax.getText());
             ellMin= Double.parseDouble(this.ellipticitymin.getText());
             bright= Double.parseDouble(this.brightness.getText());
        }
        catch( NumberFormatException e){
            this.info.setText("INPUT NON VALIDO");
            return;
        }
        UserRecorded boundary= new UserRecorded();

        try {
            infoFilaments=boundary.searchFilamentByBrightnessAndEllipticity(bright,new double[] {ellMin,ellMax});
        } catch (SQLException e) {
            e.printStackTrace();
            this.info.setText(e.getMessage());
        }
        catch (IllegalArgumentException e2){
            this.info.setText(e2.getMessage());
        }
        int numPage=1;
        if(infoFilaments!=null) {
            numPage = infoFilaments.size() / Pagination_base.itemPerPage + 1;
            System.out.println(infoFilaments.size());
        }
        pagination.setPageCount(numPage);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer param) {
                    return createPage(infoFilaments ,param) ;
                }
            });
//            int sizeList=infoFilaments.size();
//            pagination.setPageCount(sizeList/Pagination_base.itemPerPage+sizeList%Pagination_base.itemPerPage==0?1:0);
//            pagination.setMaxPageIndicatorCount(sizeList/Pagination_base.itemPerPage+sizeList%Pagination_base.itemPerPage==0?1:0);

    }
    }

