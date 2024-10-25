package com.desktopapp;

import com.desktopapp.model.UserData;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        Scene scene = Authentication.CreateScene();
        arg0.setScene(scene);
        arg0.show();
    }
}