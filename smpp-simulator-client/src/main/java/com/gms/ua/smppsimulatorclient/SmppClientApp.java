package com.gms.ua.smppsimulatorclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class SmppClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SmppClientApp.class.getResource("smpp-simulator-client.fxml"));
        Scene scene = fxmlLoader.load();
        stage.setTitle("Smpp Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
 /*private static String[] savedArgs;
    private ConfigurableApplicationContext context;

    @Value("Smpp client (protocol v3.4)")
    private String windowTitle;

    @Autowired
    @Qualifier("getRoot")
    private Parent parent;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(SmppClientApp.class, args);
    }

    private static void launchApp(Class<? extends Application> clazz, String[] args) {
        SmppClientApp.savedArgs = args;
        Application.launch(clazz, args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }*/
}