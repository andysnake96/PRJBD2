package GUI;

import BOUNDARY.UserAdministrator;
import CONTROLLER.parse.Import2DB;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Import implements Initializable {
    @FXML
    Button takeExt;
    @FXML
    Button take;
    @FXML
    TextField path;
    @FXML
    TextField info;
    @FXML
    TextField nameStr;
    @FXML
    ComboBox<String> kindCSV;
    @FXML
    ComboBox<String> group;


    public void initialize(){
        final List<String> groups=new ArrayList<>();
        groups.add(Import2DB.HERSCHEL);
        groups.add(Import2DB.SPITZER);
        final List<String> kinds=new ArrayList<>();
        kinds.add(Import2DB.OUTLINE);
        kinds.add(Import2DB.STAR);
        kinds.add(Import2DB.FILAMENT);
        kinds.add(Import2DB.SKELETONPOINT);
        group.setItems(FXCollections.observableArrayList(groups));
        kindCSV.setItems(FXCollections.observableArrayList(kinds));
    }
    @FXML
    public void back(javafx.event.ActionEvent e) throws Exception {
        ViewSwap.getIstance().swap(e,ViewSwap.MENU);
    }
    @FXML
    protected void importExt(){
        this.info.setText("");
        String path=this.path.getText();
        String nameStr=this.nameStr.getText();
        String kind=this.kindCSV.getValue();
        UserAdministrator boundary=new UserAdministrator();
        Task<String> task= new Task<String>() {
            @Override
            protected String call() throws Exception {
                return boundary.importExternCSV(path,kind,nameStr);
            }
        };
        new Thread(task).start();
        String importResult= null;
        try {
            importResult = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(importResult);

        this.info.setText(importResult);
    }
    @FXML
    protected void importGroup(){
        System.out.println("GUI"+Thread.currentThread().getName());

        this.info.setText("");
        String groupChoiced=this.group.getValue();
        UserAdministrator boundary=new UserAdministrator();
        String importResult= null;
        try {
            //IMPORT SLOW=>NOT DONE ON GUI THREAD TO AVOID FREEZE APP CRUSH
            Task<String> task= new Task<String>() {
                @Override
                protected String call() throws Exception {
                    System.out.println(Thread.currentThread().getName());
                    return boundary.readSetOfCSV(groupChoiced);
                }
            };
            new Thread(task).start();
            importResult= task.get();
            importResult = boundary.readSetOfCSV(groupChoiced);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.info.setText(importResult);
    }
}
