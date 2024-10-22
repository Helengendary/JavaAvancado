package com.desktopapp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.desktopapp.model.Materia;

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
import javafx.stage.Stage;

public class ShowMaterias implements Initializable {
    public static Scene CreateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(ShowMaterias.class.getResource("amostraMaterias.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    @FXML
    protected Button newmate;

    @FXML
    protected TableView<Materia> tabelo;

    @FXML
    protected TableColumn<Materia, String> colID;

    @FXML
    protected TableColumn<Materia, String> colNOME;

    @FXML
    protected TableColumn<Materia, String> colPROFE;

    @FXML
    protected TableColumn<Materia, String> colCARGA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        colNOME.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        colPROFE.setCellValueFactory(
                new PropertyValueFactory<>("professor"));
        colCARGA.setCellValueFactory(
                new PropertyValueFactory<>("cargaHoraria"));

        tabelo.setItems(listaDeMaterias());
    }

    @FXML
    protected void newmate(ActionEvent e) throws Exception {

        var crrStage = (Stage)newmate
                .getScene().getWindow();
            crrStage.close();
     
            var stage = new Stage();
            var scene = CadastrarMateria.CreateScene();
            stage.setScene(scene);
            stage.show();

        
    }

    private ObservableList<Materia> listaDeMaterias() {
        Context ctx = new Context();
        var query = ctx.create(Materia.class,
           "from Materia");
        
        List<Materia> mates = query.getResultList();   

        return FXCollections.observableArrayList(mates);
    }
}