package GUI;

import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pagination_base {
    @FXML
    Pagination pagination;
    @FXML
    Button buttonBK;
    @FXML
    Label requisiteLabel;
    protected static final  int itemPerPage=20;

    public void initialize(){
        System.out.println("imin");
        List<String> list=new ArrayList<>();
        for(int x=0;x<100;x++)
            list.add(String.valueOf(x));
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(list,param);
            }
        });
    }
        @FXML
    private void back(){
        final int MAX=1000;
        List<String> listStr= new ArrayList<>();
        Random random= new Random();
        for (int x=0;x<MAX;x++){
            listStr.add(String.valueOf(x*100));
        }

    }

    protected static  <T> VBox createPage(List<T> list,int pageSelected) {
        //pagesel 0,1,2
        VBox vBox= new VBox(0);
        int endIndex,startIndex;
        ObservableList<Node> children= vBox.getChildren();
        children.clear();                           //empty before new fill
        if(list.size()> itemPerPage){
            startIndex=itemPerPage*pageSelected;
            endIndex=startIndex+itemPerPage;
        }
        else {
            startIndex=0;
            endIndex=list.size();
        }
        for( int z=startIndex;z<endIndex;z++){

            Label row = new Label(list.get(z).toString());
            row.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
            children.add(row);
        }
        return vBox;
    }
}

