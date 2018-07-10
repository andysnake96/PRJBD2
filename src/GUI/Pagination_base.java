package GUI;

import BEAN.InfoFilament;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class Pagination_base {
    protected static final  int itemPerPage=20;


    protected static  <T> VBox createPage(List<T> list,int pageSelected) {
        //pagesel 0,1,2

        VBox vBox= new VBox(0);
        int endIndex,startIndex;
        ObservableList<Node> children= vBox.getChildren();
        children.clear();                           //empty before new fill
        if(list ==null || list.size()==0){
            children.add(new Label("NESSUN RISULTATO"));
            return vBox;}
        if(list.size()> itemPerPage){
            startIndex=itemPerPage*pageSelected;
            endIndex=startIndex+itemPerPage;
        }
        else {
            startIndex=0;
            endIndex=list.size();
        }
        for( int z=startIndex;z<Integer.min(endIndex,list.size());z++){
            String result=list.get(z).toString();
            Label row = new Label(result.split(InfoFilament.SEPARATORFIELDS)[0]);    //take short version (done only for infoFilament
            row.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
            children.add(row);
        }
        return vBox;
    }
}

