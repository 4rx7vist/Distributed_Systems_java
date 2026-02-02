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
        new Thread(() -> {
            new AuditService().initializeAudit();
        }).start();

        MainView root = new MainView();
        Scene scene = new Scene(root, 1000, 700);

        // Load CSS
        String css = getClass().getResource("/com/distribuidas/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setTitle("Distributed Systems - Oracle App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}