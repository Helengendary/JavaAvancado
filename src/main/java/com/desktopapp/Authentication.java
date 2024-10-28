package com.desktopapp;

import java.net.URL;
import java.util.List;

import com.desktopapp.model.UserData;

import jakarta.persistence.TypedQuery;
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
        if (!existeUser()) {
            AlertErro("Usuário não encontrado!");
            return;
        } else if (!senhaCorreta()) {
            AlertErro("Senha incorreta!");
            return;
        }

        Stage crrStage = (Stage)btEntrar
            .getScene().getWindow();
        crrStage.close();
 
        Stage stage = new Stage();
        Scene scene = ShowMensagens.CreateScene(Nome.getText());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {
        Stage crrStage = (Stage)btCadastrar
            .getScene().getWindow();
        crrStage.close();

        Stage stage = new Stage();
        Scene scene = CadastrarUsuario.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    public boolean existeUser() {
        Context ctx = new Context();

        TypedQuery<UserData> queryNome = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        queryNome.setParameter("user", Nome.getText());

        TypedQuery<UserData> queryEmail = ctx.create(UserData.class,
            "from UserData u where u.email = :user");
        queryEmail.setParameter("user", Nome.getText());

        List<UserData> usersname = queryNome.getResultList();
        List<UserData> usersemail = queryEmail.getResultList();

        if (usersname.size() > 0 || usersemail.size() > 0) {
            return true; 
        }

        return false;
    }

    public boolean senhaCorreta() {
        Context ctx = new Context();

        TypedQuery<UserData> queryNome = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        queryNome.setParameter("user", Nome.getText());

        TypedQuery<UserData> queryEmail = ctx.create(UserData.class,
            "from UserData u where u.email = :user");
        queryEmail.setParameter("user", Nome.getText());

        List<UserData> usersname = queryNome.getResultList();
        List<UserData> usersemail = queryEmail.getResultList();

        for (UserData userData : usersname) {
             
            if (userData.getPassword().equals(Senha.getText())) {
                return true;
            }
        }

        for (UserData userData : usersemail) {
             
            if (userData.getPassword().equals(Senha.getText())) {
                return true;
            }
        }

        return false;
    }

    public void AlertErro(String conteudo) {
        Alert alert = new Alert(
            AlertType.ERROR,
            conteudo,
            ButtonType.OK
        );
        alert.show();
    }
}