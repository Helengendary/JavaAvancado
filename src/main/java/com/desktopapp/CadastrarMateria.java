package com.desktopapp;

import com.desktopapp.model.Materia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarMateria {
    public static Scene CreateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(CadastrarMateria.class.getResource("materias.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    @FXML
    protected Button Voltar;

    @FXML
    protected Button Cadastrar;

    @FXML
    protected TextField inNome;

    @FXML
    protected TextField inProf;

    @FXML
    protected TextField inCarga;

    @FXML
    protected void voltara(ActionEvent e) throws Exception {

        var crrStage = (Stage)Voltar
                .getScene().getWindow();
            crrStage.close();
     
        var stage = new Stage();
        var scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void cadastrar(ActionEvent e) throws Exception {
        Materia mate = new Materia(); 
        Context ctx = new Context();
        mate.setNome(inNome.getText());
        mate.setProfessor(inProf.getText());
        mate.setCargaHoraria(inCarga.getText());
        
        ctx.begin();
        ctx.save(mate);
        ctx.commit();

        var crrStage = (Stage)Voltar
                .getScene().getWindow();
            crrStage.close();
     
        var stage = new Stage();
        var scene = ShowMaterias.CreateScene();
        stage.setScene(scene);
        stage.show();

    }

}
