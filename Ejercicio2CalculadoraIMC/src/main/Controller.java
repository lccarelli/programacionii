/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author robot
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtAltura;

    @FXML
    private Label lblResultado;

    @FXML
    private void onCalcular() {
        String pesoStr = txtPeso.getText();
        String alturaStr = txtAltura.getText();

        lblResultado.getStyleClass().removeAll(
                "error", "underweight", "normal", "overweight", "obese"
        );

        try {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (peso <= 0 || altura <= 0) {
                lblResultado.setText("Error: peso y altura deben ser mayores a 0.");
                if (!lblResultado.getStyleClass().contains("error")) {
                    lblResultado.getStyleClass().add("error");
                }
                return;
            }

            double imc = peso / (altura * altura);
            String categoria;
            String cssClass;

            if (imc < 18.5) {
                categoria = "Bajo peso";
                cssClass = "underweight";
            } else if (imc < 25) {
                categoria = "Normal";
                cssClass = "normal";
            } else if (imc < 30) {
                categoria = "Sobrepeso";
                cssClass = "overweight";
            } else {
                categoria = "Obesidad";
                cssClass = "obese";
            }

            lblResultado.setText(String.format("IMC: %.2f - %s", imc, categoria));
            lblResultado.getStyleClass().add(cssClass);

        } catch (NumberFormatException e) {
            lblResultado.getStyleClass().add("error");
            lblResultado.setText("Error: ingresá valores numéricos válidos.");
        }
    }

}
