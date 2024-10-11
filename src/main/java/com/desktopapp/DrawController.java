package com.desktopapp;

import java.util.ArrayList;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class DrawController implements Initializable {

    public static Scene CreateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(DrawController.class.getResource("canva.fxml"));
        Parent root = loader.load();

        return new Scene(root);
    }
    
    Timer timer = new Timer();

    @FXML
    protected Canvas canva ;
    
    @FXML
    protected VBox box ;

    private ArrayList<Float> values = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private int selected = -1;

    public void add(Float value, Color color, String desc) {
        this.values.add(value);
        this.colors.add(color);
        this.desc.add(desc);
    }

    private void circulo() {
        add(10f, Color.BLUE, "azul");
        add(10f, Color.PINK, "azul");
        add(10f, Color.RED, "azul");
    }
    
    @FXML
    protected void interact(MouseEvent e){
        double width = canva.getWidth();
        double height = canva.getHeight();
        double total = values.stream().reduce(0f, (a, x) -> a + x);

        double cx = width / 2;
        double cy = height / 2;
        double dx = e.getX() - cx; 
        double dy = e.getY() - cy; 

        double angle = 180 * Math.atan2(dy, -dx) / Math.PI + 180;

        double distance = Math.sqrt(dx*dx + dy*dy);
        if (distance > width / 2) {
            selected = -1;
            toCircle();
            box.requestLayout();
            return;
        }

        double currentArc = 0;

        for (int i = 0; i < values.size(); i++) {
            Float value = values.get(i);

            double arc = 360 * value / total;
            double initialAngle = currentArc;
            double finalAngle = currentArc + arc;
            currentArc = finalAngle;

            if (angle > initialAngle && angle < finalAngle) {
                selected = i;
            }
        }

        toCircle();
        box.requestLayout();
    }

    @FXML
    protected void pressed(MouseEvent e){

    }

    @FXML
    protected void released(MouseEvent e){

    }

    @Override 
    public void initialize(URL url, ResourceBundle re) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                rotation += Math.PI /20;
                drawStar();
                box.requestLayout();
            }
        }, 50, 50);
        drawStar();
    }

    double rotation;
    private void drawStar() {
        var g = canva.getGraphicsContext2D();
        g.clearRect(0, 0, canva.getWidth(), canva.getHeight());
        
        double[] xs = new double[10];
        double[] ys = new double[10];

        double theta = 0f;

        for (int i = 0; i < ys.length; i++) {
            double rho = i % 2 == 0 ? 200 : 80;

            xs[i] = rho * Math.cos(theta);
            ys[i] = rho * Math.sin(theta);


            theta += 2 * Math.PI / 10;
        }

        for (int i = 0; i < 10; i++) {
            
            var x = xs[i];
            var y = ys[i];

            xs[i] = x * Math.cos(rotation) + y * Math.sin(rotation);
            ys[i] = x * Math.sin(rotation) - y * Math.cos(rotation);
        }

        for (int i = 0; i < 10; i++) {
            xs[i] += canva.getWidth() / 2;
            ys[i] += canva.getHeight() / 2;
        }

        g.fillPolygon(xs, ys, 10);
    }

    private void drawCircle() {
        circulo();
        toCircle();
    }

    private void toCircle() {
        var g = canva.getGraphicsContext2D();


        double width = canva.getWidth();
        double height = canva.getHeight();
        double total = values.stream().reduce(0f, (a, x) -> a + x);

        double currentArc = 0;

        for (int i = 0; i < values.size(); i++) {
            Float value = values.get(i);
            Color color = colors.get(i);

            double arc = 360 * value / total;


            if (selected == i) {
                color = new Color(0.6 * color.getRed() + 0.4, 0.6 * color.getGreen() + 0.4, 0.6 * color.getBlue() + 0.4, 1F);
            }

            g.setFill(color);
            g.fillArc(0, 0, width, height, currentArc, arc, ArcType.ROUND);

            currentArc += arc;
        }
    }
}