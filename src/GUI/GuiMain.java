package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application implements Runnable {
    protected static String profId = null;

    public static void main(String[] args) {

        launch(args);
    }

    //GUI=>1! user==> profId can be set and the setted from package
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewSwap.LOGGIN));
        primaryStage.setTitle("PROGETTO BASI DI DATI");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void run() {
        launch();
    }
}
