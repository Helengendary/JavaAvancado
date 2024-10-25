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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExcluirMateria {
    public static Scene CreateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(ExcluirMateria.class.getResource("excluirMaterias.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    int paraEditar = 0;
    boolean existeID = false;
    ObservableList<Materia> lista =  listaDeMaterias();

    @FXML
    protected Button pesID;

    @FXML
    protected Button confirmExcluir;

    @FXML
    protected TextField inID;

    @FXML
    protected Text infoNome;

    @FXML
    protected Text infoProfe;

    @FXML
    protected Text infoCarga;
    
    @FXML
    protected void pesquisar(ActionEvent e) throws Exception {
        existeID = false;
        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getId().toString().equals(inID.getText())) {
                existeID = true;
                paraEditar = i;

                infoNome.setText(lista.get(i).getNome());
                infoProfe.setText(lista.get(i).getProfessor());
                infoCarga.setText(lista.get(i).getCargaHoraria());
            }
        }

        if (!existeID) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "id não encontrado",
                ButtonType.OK);
            alert.show();
            return;
        }
    }

    @FXML
    protected void excluirReal(ActionEvent e) throws Exception {
        
        Context ctx = new Context();
        TypedQuery<Object> query = ctx.create(null, "Delete Materia Where id = :id ");
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
        Stage crrStage = (Stage)confirmExcluir
                .getScene().getWindow();
            crrStage.close();
    
        Stage stage = new Stage();
        Scene scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}