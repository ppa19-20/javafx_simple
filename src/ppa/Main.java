package ppa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller ctrl = loader.getController();
        primaryStage.setTitle("PPA 02");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.getScene().getStylesheets().add("ppa/sample.css");
        primaryStage.show();
        ctrl.setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
