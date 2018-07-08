package GUI;

import BOUNDARY.UserRecorded;
import feauture1.Bean.InfoFilament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class OutputNSeg implements Initializable{

    @FXML
    private TableView<TableValue> tableViewPagination;

    @FXML
    private TableColumn<TableValue, String> id, name, ell, con, flux, temp, dens ,nSeg, nameStr;

    @FXML
    private Pagination pag;

    int from = 0, to = 0, itemPerPage = 20;

    private List<InfoFilament> infoFilaments;

    public OutputNSeg(List<InfoFilament> infoFilaments) {
        this.infoFilaments = infoFilaments;
    }


    @FXML
    public void initialize() {
        int count = infoFilaments.size();

        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.ell.setCellValueFactory(new PropertyValueFactory<>("ell"));
        this.con.setCellValueFactory(new PropertyValueFactory<>("con"));
        this.flux.setCellValueFactory(new PropertyValueFactory<>("flux"));
        this.temp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        this.dens.setCellValueFactory(new PropertyValueFactory<>("dens"));
        this.nSeg.setCellValueFactory(new PropertyValueFactory<>("nSeg"));
        this.nameStr.setCellValueFactory(new PropertyValueFactory<>("nameStr"));

        int pageCount = (count / itemPerPage) +1;
        this.pag.setPageCount(pageCount);
        pag.setPageFactory(this::createPage);


    }

    private Node createPage(Integer pageIndex) {
        System.out.println(pageIndex);
        from = pageIndex*itemPerPage;
        to = itemPerPage*(pageIndex+1)
        ;
        tableViewPagination.setItems(FXCollections.observableArrayList(getTableValue()));
        return tableViewPagination;
    }

    private List<TableValue> getTableValue() {
    List<TableValue> tableValues = new ArrayList<>();
    System.out.println("Sono qui");
    for(int index = from; index < to; index++) {
            InfoFilament i = infoFilaments.get(index);
            if(i == null) {
                System.out.println("qkkdfccccccc");
            }
            String nSeg = String.valueOf(i.getnSeg());
            //System.out.println(nSeg);
            TableValue tableValue = new TableValue(String.valueOf(i.getId()), i.getName(), i.getEllipticity().toString(),i.getContrast().toString()
                                                    , i.getFluxTot().toString(), i.getTempAvg().toString(), i.getDensAvg().toString()
                                                        , String.valueOf(i.getnSeg()), i.getNameStr());
            tableValues.add(tableValue);
    }
    return tableValues;


}
    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }
}