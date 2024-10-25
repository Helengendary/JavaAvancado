package com.desktopapp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.desktopapp.model.Materia;

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
    protected Button editmate;

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

        Stage crrStage = (Stage)newmate
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = CadastrarMateria.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void edite(ActionEvent e) throws Exception {

        Stage crrStage = (Stage)editmate
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = EditarMateria.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void excloi(ActionEvent e) throws Exception {

        Stage crrStage = (Stage)editmate
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = ExcluirMateria.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    public static ObservableList<Materia> listaDeMaterias() {
        Context ctx = new Context();
        TypedQuery<Materia> query = ctx.create(Materia.class, "from Materia");
        
        List<Materia> mates = query.getResultList();   

        return FXCollections.observableArrayList(mates);
    }

}
