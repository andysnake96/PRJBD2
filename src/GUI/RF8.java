package GUI;

import BOUNDARY.UserRecorded;
import BEAN.InfoFilament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

import static GUI.Pagination_base.createPage;

public class RF8 {
    @FXML
    Label info;
    @FXML
    Button kindRegion;
    @FXML
    Pagination pagination;
    @FXML
    TextField regionDimension;
    @FXML
    TextField glat;
    @FXML
    TextField regionDimensionLab;
    @FXML
    TextField glon;
    List<InfoFilament> infoFilaments;

    private final int CIRCLEKIND=-96;
    private final int SQUAREKIND=96;
    private int regionKind=CIRCLEKIND;
    public void back(ActionEvent actionEvent) throws Exception {
        ViewSwap.getIstance().swap(actionEvent,ViewSwap.MENU);
    }

    public void search() {
        //getting input values
        if(this.infoFilaments!=null)
            infoFilaments.clear();
        this.info.setText("");  //clear prev
        double dimensionRegion;
        double glat;
        double glon;
        try{
        glat= Double.parseDouble(this.glat.getText());
        glon= Double.parseDouble(this.glon.getText());
        dimensionRegion= Double.parseDouble(this.regionDimension.getText());}
        catch (NumberFormatException e){
            this.info.setText("INPUT NON VALIDO");
            return;
        }
        UserRecorded boundary= new UserRecorded();

        try {
            if(regionKind==CIRCLEKIND)
                this.infoFilaments=boundary.searchFilamentByCircle(glat,glon,dimensionRegion);
            else
                this.infoFilaments=boundary.searchFilamentBySquare(glat,glon,dimensionRegion);
        } catch (SQLException e) {
            e.printStackTrace();
            this.info.setText(e.getMessage());
        }
        catch (IllegalArgumentException e2){        //argument check in boundary
            this.info.setText(e2.getMessage());
        }
        int numPage=1;
        if(infoFilaments!=null)
            numPage=infoFilaments.size()/Pagination_base.itemPerPage +1;
        pagination.setPageCount(numPage);
        System.out.println(infoFilaments==null);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer param) {
                    return createPage(infoFilaments ,param) ;
                }
            });
        }

    @FXML
    protected void changeKind(){
        if(regionKind==CIRCLEKIND){
            regionKind=SQUAREKIND;
            regionDimensionLab.setText("Lato");
            kindRegion.setText("Quadrato");
        }
        else{
            regionKind=CIRCLEKIND;
            regionDimensionLab.setText("Raggio");
            kindRegion.setText("Cerchio");

        }
    }
    }