package GUI;

import BOUNDARY.UserRecorded;
import BEAN.BeanRF12;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import static GUI.Pagination_base.createPage;

public class RF12 {
    @FXML
    Label info;

    @FXML
    Button kindSearch;
    @FXML
    Button kindSort;

    @FXML
    Pagination pagination;

    @FXML
    TextField kindSearchLabel;

    @FXML
    TextField filSearchID;
    @FXML
    TextField nameStr;

    @FXML
    TextField nameStrLabel;

    List<BeanRF12> result;

    private final int IDSEARCHKIND=-96;
    private final int NAMESEARCHKIND=96;
    private final int DISTANCESORT=5;
    private final int FLUXSORT=-5;
    private int sortKind=DISTANCESORT;
    private int regionKind=IDSEARCHKIND;
    public void back(ActionEvent actionEvent) throws Exception {
        ViewSwap.getIstance().swap(actionEvent,ViewSwap.MENU);
    }

    public void search() {
        //getting input values
        if(result!=null)
            result.clear();

        this.info.setText("");  //clear prev
        String filID=filSearchID.getText();
        String nameStr=this.nameStr.getText();
        UserRecorded boundary= new UserRecorded();
        int idFil;
        try {
            if(regionKind==IDSEARCHKIND){
                try{idFil=Integer.parseInt(filID);}
                catch (NumberFormatException e){
                    this.info.setText("INUPUT NON VALIDO");
                    return;
                }
                result= boundary.allStarInFilMinDist(idFil,nameStr);}
            else
                result= boundary.allStarInFilMinDist(filID);
        } catch (SQLException e) {
            e.printStackTrace();
            this.info.setText(e.getMessage());
            return;
        }
        catch (IllegalArgumentException e2){        //argument check in boundary
            this.info.setText(e2.getMessage());
            return;
        }
        int numPage=0;
        if(result!=null)
            numPage=result.size()/Pagination_base.itemPerPage +1;
        pagination.setPageCount(numPage);
        if (sortKind==FLUXSORT)
            result.sort(Comparator.comparingDouble(BeanRF12::getFlux));
        else
            result.sort(Comparator.comparingDouble(BeanRF12::getDistance));

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(result ,param) ;
            }
        });


    }
    @FXML
    protected void changeKindSort(){
        if(this.sortKind==DISTANCESORT) {
            sortKind = FLUXSORT;
            kindSort.setText("Flusso");

        }
        else{
            sortKind=DISTANCESORT;
            kindSort.setText("Distanza");
        }}
    @FXML
    protected void changeKindSearch(){
        /*
        handle searching by name or id,namestr...
        something has to change displayed mode
         */
        if(regionKind==IDSEARCHKIND){
            regionKind=NAMESEARCHKIND;
            kindSearchLabel.setText("nomeFilamento");
            nameStr.setVisible(false);
            nameStrLabel.setVisible(false);
            this.kindSearch.setText("NOME FILAMENTO");
        }
        else{
            regionKind=IDSEARCHKIND;
            kindSearchLabel.setText("ID Filamento");
            this.kindSearch.setText("ID FILAMENTO");

            nameStr.setVisible(true);
            nameStrLabel.setVisible(true);
        }
    }
}