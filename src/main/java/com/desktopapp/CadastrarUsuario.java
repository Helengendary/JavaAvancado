package com.desktopapp;

import java.net.URL;
import java.util.List;

import com.desktopapp.model.UserData;

import jakarta.persistence.TypedQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

        TypedQuery<UserData> query = ctx.create(UserData.class,
            "from UserData u where u.name = :user");

        query.setParameter("user", name_cad.getText());

        TypedQuery<UserData> temPergunta = ctx.create(UserData.class,
        "from UserData");

        List<UserData> users = query.getResultList();
        List<UserData> tem = temPergunta.getResultList();

        if (users.size() == 0 && tem.size() > 0) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Usuário já existente",
                ButtonType.OK
            );
            alert.show();
            return;
        }
        

        user.setName(name_cad.getText());
        user.setPassword(pass_cad.getText());
        
        ctx.begin();
        ctx.save(user);
        ctx.commit();

        Stage crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        Stage stage = new Stage();
        Scene scene = Authentication.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void toLogin(ActionEvent e) throws Exception {
        Stage crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        Stage stage = new Stage();
        Scene scene = Authentication.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}