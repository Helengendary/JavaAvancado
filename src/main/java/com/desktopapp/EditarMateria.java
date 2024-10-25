package com.desktopapp;

import java.util.List;

import com.desktopapp.model.Materia;
import com.desktopapp.model.UserData;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarMateria {
    public static Scene CreateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(EditarMateria.class.getResource("editarMaterias.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    int paraEditar = 0;
    ObservableList<Materia> lista =  listaDeMaterias();

    @FXML
    protected Button pesID;

    @FXML
    protected Button confirmEdit;

    @FXML
    protected TextField inID;

    @FXML
    protected TextField inNomeEdit;

    @FXML
    protected TextField inProfeEdit;

    @FXML
    protected TextField inCargaEdit;

    
    @FXML
    protected void pesquisar(ActionEvent e) throws Exception {

        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getId().toString().equals(inID.getText())) {
                paraEditar = i;

                inNomeEdit.setText(lista.get(i).getNome());
                inProfeEdit.setText(lista.get(i).getProfessor());
                inCargaEdit.setText(lista.get(i).getCargaHoraria());
            }
        }
    }



    @FXML
    protected void editarReal(ActionEvent e) throws Exception {
        
        Context ctx = new Context();
        TypedQuery<Object> query = ctx.create(null, "Update Materia Set nome = :nome, professor = :profe, cargaHoraria = :carga Where id = :id ");
        query.setParameter("nome", inNomeEdit.getText());
        query.setParameter("profe", inProfeEdit.getText());
        query.setParameter("carga", inCargaEdit.getText());
        query.setParameter("id", inID.getText());

        query.executeUpdate();

        ctx.commit();

        voltar();
    }
  

    // to usando
    public static ObservableList<Materia> listaDeMaterias() {
        Context ctx = new Context();
        TypedQuery<Materia> query = ctx.create(Materia.class, "from Materia");
        
        List<Materia> mates = query.getResultList();   

        return FXCollections.observableArrayList(mates);
    }
    
    public void voltar() throws Exception {
        Stage crrStage = (Stage)confirmEdit
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}
