package com.view;

import com.view.ctrl.NCSmartClientCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/NCSmartClient.fxml"));
        Parent root = loader.load();
        NCSmartClientCtrl ctrl = loader.getController();
        ctrl.onload();
        primaryStage.setTitle("NC智能客户端");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
