package com.desktopapp;

import java.net.URL;
import java.util.List;

import com.desktopapp.model.UserData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class Authentication {

    public static Scene CreateScene() throws Exception {
        URL sceneUrl = Authentication.class.getResource("login.fxml");
        Parent Root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(Root);
        return scene;
    }

    @FXML
    protected Button btEntrar;

    @FXML
    protected TextField Nome;

    @FXML
    protected PasswordField Senha;

    @FXML
    protected Button btCadastrar;

    @FXML
    protected void entrar(ActionEvent e) throws Exception {
        Context ctx = new Context();

        var query = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        query.setParameter("user", Nome.getText());
        List<UserData> users = query.getResultList();

        if (users.size() == 0) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Usuário não encontrado",
                ButtonType.OK
            );
            alert.show();
            return;
        }
        
        var user = users.get(0);

        if (!Senha.getText().equals(user.getPassword())) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Senha incorreta!",
                ButtonType.OK
            );
            alert.show();
            return;
        }

        var crrStage = (Stage)btEntrar
            .getScene().getWindow();
        crrStage.close();
 
        var stage = new Stage();
        var scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {
        var crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = CadastrarUsuario.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}