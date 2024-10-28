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
import javafx.scene.control.PasswordField;
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
    protected PasswordField pass_cad;

    @FXML
    protected PasswordField confPass_cad;
    
    @FXML
    protected TextField email_cad;

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {

        UserData user = new UserData(); 
        Context ctx = new Context();

        padraEmail();

        if (existeUser()) {
            if (existeEmail()) {
                AlertErro("Nome de usuário e e-mail já cadastrados!");
                return;
            }else {
                AlertErro("Nome de usuário já cadastrado!");
                return;
            }
        } else if (existeEmail()) {
            AlertErro("E-mail já cadastrado!");
            return;
        } else if (!senhaIguais()) {
            AlertErro("As senhas não são iguais!");
            return;
        } else if (!padraEmail()) {
            AlertErro("Insira um e-mail válido!");
            return;
        } else if (!senhaValida()) {
            AlertErro("A senha deve conter caracteres especiais, letras (sem ascento) e números. E no mínimo 8 caracteres!");
            return;
        }

        user.setName(name_cad.getText());
        user.setPassword(pass_cad.getText());
        user.setEmail(email_cad.getText());

        ctx.begin();
        ctx.save(user);
        ctx.commit();

        toLogin(e);
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

    public boolean existeUser() {
        Context ctx = new Context();

        TypedQuery<UserData> query = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        query.setParameter("user", name_cad.getText());

        List<UserData> users = query.getResultList();

        if (users.size() > 0) {
            return true; 
        }

        return false;
    }

    public boolean existeEmail() {
        Context ctx = new Context();

        TypedQuery<UserData> query = ctx.create(UserData.class,
            "from UserData u where u.email = :email");
        query.setParameter("email", name_cad.getText());

        List<UserData> users = query.getResultList();

        if (users.size() > 0) {
            return true; 
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

    public boolean senhaIguais() {
        
        if (pass_cad.getText().equals(confPass_cad.getText())) {
            return true;
        }

        return false;
    }
  
    public boolean padraEmail() {
        boolean arroba = false;

        for (int i = 0; i < email_cad.getText().length(); i++) {
            char string = email_cad.getText().toLowerCase().charAt(i);

            if (string == '@' && i > 0 && email_cad.getText().toLowerCase().charAt(i+1) != '.')  {
                arroba = true;
            }

            if (string == '.' && arroba && i < email_cad.getText().length()-1) {
                return true;
            }
        }

        return false;
    }

    public boolean senhaValida() {

        boolean especiais = false;
        boolean numeros = false;
        boolean letras = false;

        for (int i = 0; i < pass_cad.getText().length(); i++) {
            char string = pass_cad.getText().toLowerCase().charAt(i);

            if (string == '@' || string == '!' || string == '#' || string == '$' || string == '%' || string == '&' || string == '*' || string == '?' || string == '+' || string == '=') {
                especiais = true;
            } else if (pass_cad.getText().chars().anyMatch(c -> c >= '0' && c <= '9')) {
                numeros = true;
            }

            if (string == 'q' || string == 'w' || string == 'e' || string == 'r' || string == 't' || string == 'y' || string == 'u' || string == 'i' || string == 'o' || string == 'p' || string == 'a' || string == 's' || string == 'd' || string == 'f' || string == 'g' || string == 'h' || string == 'j' || string == 'k' || string == 'l' || string == 'z' || string == 'x' || string == 'c' || string == 'v' || string == 'b' || string == 'n' || string == 'm') {
                letras = true;
            }
        }

        if (numeros && especiais && letras && pass_cad.getText().length() >= 8) {
            return true;
        }

        return false;
    }
}