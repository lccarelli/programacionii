/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 780, 480);
        stage.setTitle("Buscador de Contactos");
        stage.setScene(scene);
        stage.setMinWidth(780);
        stage.setMinHeight(480);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
