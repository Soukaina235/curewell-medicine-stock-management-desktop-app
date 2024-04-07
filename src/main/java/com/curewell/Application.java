package com.curewell;

import com.curewell.model.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Employee currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.initStyle(StageStyle.DECORATED);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}