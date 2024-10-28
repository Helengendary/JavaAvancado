package com.desktopapp;

import java.util.List;

import com.desktopapp.model.Mensagem;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EscreverEmail {
    public static Scene CreateScene(String nome) throws Exception {
        FXMLLoader loader = new FXMLLoader(EscreverEmail.class.getResource("escrever.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EscreverEmail controller = loader.getController();
        controller.nome = nome;
        return scene;
    }

    @FXML
    protected Button Voltar;

    @FXML
    protected Button enviar;

    @FXML
    protected TextField titulo;

    @FXML
    protected TextField destino;

    @FXML
    protected TextArea sms;

    String nome;

    @FXML
    protected void voltara(ActionEvent e) throws Exception {

        Stage crrStage = (Stage)Voltar
                .getScene().getWindow();
            crrStage.close();
     
        Stage stage = new Stage();
        Scene scene = ShowMensagens.CreateScene(nome);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {
        Mensagem mate = new Mensagem(); 
        Context ctx = new Context();

        if (existeUser()) {
            AlertErro("E-mail não encontrado!");
            return;
        }
        
        mate.setMensagem(sms.getText());
        mate.setTitulo(titulo.getText());
        mate.setRecebe(destino.getText());
        mate.setEnvia(acharEmail());

        ctx.begin();
        ctx.save(mate);
        ctx.commit();

        Stage crrStage = (Stage)Voltar
                .getScene().getWindow();
            crrStage.close();
     
        Stage stage = new Stage();
        Scene scene = ShowMensagens.CreateScene(nome);
        stage.setScene(scene);
        stage.show();

    }

    public boolean existeUser() {
        Context ctx = new Context();

        TypedQuery<UserData> queryEmail = ctx.create(UserData.class,
            "from UserData u where u.email = :user");
        queryEmail.setParameter("user", destino.getText());

        List<UserData> usersemail = queryEmail.getResultList();

        if (usersemail.size() > 0) {
            return true; 
        }

        return false;
    }

    public String acharEmail() {
        Context ctx = new Context();

        TypedQuery<UserData> queryEmail = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        queryEmail.setParameter("user", nome);

        List<UserData> usersemail = queryEmail.getResultList();

        for (UserData userData : usersemail) {
            if (userData.getName().equals(nome)) {
                return userData.getEmail(); 
            }
        }
        return null;
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
