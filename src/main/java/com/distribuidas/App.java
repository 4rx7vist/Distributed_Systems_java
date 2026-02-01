package com.distribuidas;

import com.distribuidas.service.AuditService;
import com.distribuidas.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Initialize Audit logic in background to fix schema if needed
        // Initialize Audit logic in background
        new Thread(() -> {
            new AuditService().initializeAudit();
        }).start();

        com.distribuidas.view.LoginView loginView = new com.distribuidas.view.LoginView(stage, () -> {
            MainView root = new MainView();
            Scene scene = new Scene(root, 1000, 700);

            // Load CSS
            try {
                String css = getClass().getResource("/com/distribuidas/styles.css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e) {
                System.out.println("CSS not found");
            }

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(true);
        });

        Scene loginScene = new Scene(loginView, 400, 350);
        stage.setScene(loginScene);
        stage.setTitle("Distribuidas - Login");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
