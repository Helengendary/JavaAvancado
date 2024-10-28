package com.desktopapp;

import java.util.ArrayList;

import com.desktopapp.model.UserData;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    static ArrayList<UserData> usersExistentes;
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