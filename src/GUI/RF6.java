package GUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static GUI.Pagination_base.createPage;


public class RF6 {
    @FXML
    Pagination pagination;
    @FXML
    Button buttonBK;

    @FXML
    Label labelRF;

    @FXML
    TextField ellipticitymin;

    @FXML
    TextField ellipticitymax;

    @FXML
    Label info;


    public void initialize(){
        System.out.println("imin");
        List<String> list=new ArrayList<>();
        for(int x=0;x<100;x++)
            list.add(String.valueOf(x));
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(list ,param) ;
            }
        });
    }
    @FXML
    protected void back(){
        System.out.println("bavk");
    }
}
