package com.desktopapp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.desktopapp.model.Mensagem;
import com.desktopapp.model.UserData;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowMensagens implements Initializable {

    public static Scene CreateScene(String nome) throws Exception {
        FXMLLoader loader = new FXMLLoader(ShowMensagens.class.getResource("entrada.fxml"));
        
        Parent root = loader.load();

        ShowMensagens controller = loader.getController();
        controller.emailatual = nome;

        return new Scene(root);
    }

    @FXML
    protected Button novoEmail;

    @FXML
    protected TableView<Mensagem> emails;

    @FXML
    protected TableColumn<Mensagem, String> colTitulo;

    @FXML
    protected TableColumn<Mensagem, String> colMensageiro;

    @FXML
    protected Text apresenta;

    String emailatual = "helena";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colTitulo.setCellValueFactory(
            new PropertyValueFactory<>("titulo"));
        colMensageiro.setCellValueFactory(
            new PropertyValueFactory<>("envia"));
                

        emails.setItems(listaDeMensagem());
        
    }

    @FXML
    protected void newmate(ActionEvent e) throws Exception {

        Stage crrStage = (Stage)novoEmail
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = EscreverEmail.CreateScene(apresenta.getText());
        stage.setScene(scene);
        stage.show();
    }

    public String acharEmail() {
        Context ctx = new Context();

        TypedQuery<UserData> queryEmail = ctx.create(UserData.class,
            "from UserData u where u.name = :user");
        queryEmail.setParameter("user", apresenta.getText());

        List<UserData> usersemail = queryEmail.getResultList();

        for (UserData userData : usersemail) {
            if (userData.getName().equals(apresenta.getText())) {
                return userData.getEmail(); 
            }
        }
        return null;
    }

    public ObservableList<Mensagem> listaDeMensagem() {
        Context ctx = new Context();

        TypedQuery<Mensagem> query = ctx.create(Mensagem.class, "from Mensagem ");

        
        List<Mensagem> sms = query.getResultList();   

        return FXCollections.observableArrayList(sms);
    }

}
