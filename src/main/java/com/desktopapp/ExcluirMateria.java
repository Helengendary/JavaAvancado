package com.desktopapp;

import java.util.List;

import com.desktopapp.model.Materia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getId().toString().equals(inID.getText())) {
                paraEditar = i;

                infoNome.setText(lista.get(i).getNome());
                infoProfe.setText(lista.get(i).getProfessor());
                infoCarga.setText(lista.get(i).getCargaHoraria());
            }
        }
    }

    @FXML
    protected void excluirReal(ActionEvent e) throws Exception {
        
        Context ctx = new Context();
        var query = ctx.create(null, "Delete Materia Where id = :id ");
        query.setParameter("id", inID.getText());

        query.executeUpdate();

        ctx.commit();

        voltar();
    }

    // to usando
    public static ObservableList<Materia> listaDeMaterias() {
        Context ctx = new Context();
        var query = ctx.create(Materia.class, "from Materia");
        
        List<Materia> mates = query.getResultList();   

        return FXCollections.observableArrayList(mates);
    }
    
    public void voltar() throws Exception {
        var crrStage = (Stage)confirmExcluir
                .getScene().getWindow();
            crrStage.close();
    
        var stage = new Stage();
        var scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
}