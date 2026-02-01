package com.distribuidas.view;

import com.distribuidas.model.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends VBox {

    private Stage stage;
    private Runnable onLoginSuccess;

    public LoginView(Stage stage, Runnable onLoginSuccess) {
        this.stage = stage;
        this.onLoginSuccess = onLoginSuccess;
        initUI();
    }

    private void initUI() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(40));
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #f4f4f4;");

        Label title = new Label("Distribuidas Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle(
                "-fx-background-color: white; -fx-background-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        Label lblUser = new Label("Usuario:");
        TextField txtUser = new TextField();
        txtUser.setPromptText("admin, quito, guayaquil");

        Label lblPass = new Label("Contraseña:");
        PasswordField txtPass = new PasswordField();

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setOnAction(e -> handleLogin(txtUser.getText(), txtPass.getText()));

        grid.add(lblUser, 0, 0);
        grid.add(txtUser, 1, 0);
        grid.add(lblPass, 0, 1);
        grid.add(txtPass, 1, 1);
        grid.add(btnLogin, 0, 2, 2, 1);

        this.getChildren().addAll(title, grid);
    }

    private void handleLogin(String user, String pass) {
        // Authenticate via Database
        try {
            com.distribuidas.db.DatabaseConnection.setCredentials(user, pass);
            // Validation check - try to get a connection
            java.sql.Connection conn = com.distribuidas.db.DatabaseConnection.getDedicatedConnection();
            if (conn == null)
                throw new Exception("Connection failed");

            // Determine logical role/location based on username
            String role = "SLAVE";
            String location = "Fragment";

            if ("master".equalsIgnoreCase(user)) {
                role = "MASTER";
                location = "ALL";
            } else if ("jrevelo".equalsIgnoreCase(user)) {
                location = "Guayaquil";
            } else if ("spinta".equalsIgnoreCase(user)) {
                location = "Quito"; // Assuming spinta is Quito per standard naming/distribution
            } else {
                location = "Unknown";
            }

            UserSession.getInstance().setUser(user, role, location);

            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }

        } catch (Exception ex) {
            String msg = "Error de conexión: " + ex.getMessage();
            if (ex.getCause() != null)
                msg += "\n" + ex.getCause().getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            alert.show();
        }

    }
}
