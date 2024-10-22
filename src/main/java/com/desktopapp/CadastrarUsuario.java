package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.UserData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarUsuario {

    public static Scene CreateScene() throws Exception {
        URL sceneUrl = CadastrarUsuario.class.getResource("cadastrar.fxml");
        Parent Root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(Root);
        return scene;
    }

    @FXML
    protected Button btCadastrar;

    @FXML
    protected TextField name_cad;

    @FXML
    protected TextField pass_cad;

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {

        UserData user = new UserData(); 
        Context ctx = new Context();
        user.setName(name_cad.getText());
        user.setPassword(pass_cad.getText());
        
        ctx.begin();
        ctx.save(user);
        ctx.commit();

        var crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = Authentication.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void toLogin(ActionEvent e) throws Exception {
        var crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = Authentication.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}