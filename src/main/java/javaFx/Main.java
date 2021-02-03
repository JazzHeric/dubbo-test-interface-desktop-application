package javaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/javaFx/sample.fxml"));
        primaryStage.setTitle("dubbo桌面测试工具");
        primaryStage.setResizable(Boolean.FALSE);
        Scene scene = new Scene(root, 1000, 900);
        scene.getStylesheets().add(getClass().getResource("/javaFx/tableview.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
