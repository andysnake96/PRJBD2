package GUI;

import BOUNDARY.UserAdministrator;
import BOUNDARY.UserLogin;
import BEAN.UserBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class RecordUserController {

    @FXML // fx:id="info"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="backToMenuButton"
    private Button backToMenuButton; // Value injected by FXMLLoader

    @FXML // fx:id="surname"
    private TextField surname; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="type"
    private ComboBox<String> type; // Value injected by FXMLLoader

    @FXML // fx:id="datoMancanteText"
    private Text info; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="username"
    private TextField username; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader


    @FXML
    void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add(UserLogin.TYPEADMIN);
        items.add(UserLogin.TYPEUSER);
        this.type.setItems(items);
    }

    @FXML
    void backToMenu(ActionEvent event) throws Exception {
        ViewSwap.getIstance().swap(event, ViewSwap.MENU);
    }

    @FXML
    void recordUser(ActionEvent event) {
        String name = this.name.getText();
        if (name.isEmpty()) {
            this.info.setText("Inserire nome");
            return;
        }
        String surname = this.surname.getText();
        if (surname.isEmpty()) {
            this.info.setText("Inserire cognome");
            return;
        }
            String username = this.username.getText();
            if(username.isEmpty())  {
                this.info.setText("Inserire l'username");
                return;
            }
            String password = this.password.getText();
            if(password.isEmpty()) {
                this.info.setText("Inserire la password");
                return;
            }
            String email = this.email.getText();
            String type = this.type.getValue();
            if(type == null) {
                info.setText("Scegliere il tipo dell'utente");
                return;
            }
            UserBean userBean = new UserBean(name, surname, username, password, email, type);
        try {
            UserAdministrator administrator = LogController.userLogin.getUserAdministrator();
            String info = administrator.recordUser(userBean);
            this.info.setText(info);
        } catch (Exception e) {
            e.printStackTrace();
            this.info.setText("Errore");
        }

    }

}