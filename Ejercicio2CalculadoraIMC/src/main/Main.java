/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author robot
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 460, 420);
        stage.setMinHeight(420);// ancho y alto inicial

        stage.setTitle("Calculadora de IMC");
        stage.setScene(scene);

        // opcional: tamaño mínimo
        stage.setMinWidth(420);
        stage.setMinHeight(420);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

