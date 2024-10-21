package com.desktopapp;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class JAVAController {
    public static Scene CreateScene() throws Exception {
        URL sceneUrl = JAVAController.class.getResource("JAVA.fxml");
        Parent Root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(Root);
        return scene;
    }
}
