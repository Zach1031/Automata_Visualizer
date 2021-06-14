import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sun.java2d.pipe.hw.AccelDeviceEventNotifier;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    HashMap<Circle, State> stateList = new HashMap<>();
    Stage editWindow;
    Line currentLine;

    public boolean onCircle(double x, double y){
        for(Circle c : stateList.keySet()){
            if(c.contains(new Point2D(x, y))){
                return true;
            }
        }

        return false;
    }

    public boolean onNode(double x, double y, Group root){
        for(Node n : root.getChildren()){
            if(n.getLayoutX() == x && n.getLayoutY() == y){
                return true;
            }
        }

        return false;
    }

    public Circle createCircle(double x, double y){
        System.out.println("Create a Circle");

        State state = new State();

        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(25.0f);
        circle.setStrokeWidth(20);

        stateList.put(circle, state);

        return circle;
    }

    public Circle getCircle(double x, double y){
        for(Circle c : stateList.keySet()){
            if(c.contains(new Point2D(x, y))){
                return c;
            }
//            if((c.getCenterX() == x) && (c.getCenterY() == y)){
//                return c;
//            }
        }

        return new Circle();
    }

    public void editCircle(Circle circle){
        editWindow = new Stage();
        Label nameLabel = new Label("Name:");
        TextField nameValue = new TextField();

        Button isFinal = new Button("Final");

        editWindow.setScene(new Scene(new Group(nameLabel, nameValue, isFinal), 500, 500));
        editWindow.show();

        System.out.println("Edit a Circle");
    }

    //THE FORMATING IS NOT CORRECT
    @Override
    public void start(Stage primaryStage) throws Exception{
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(10, 10, 10, 10));
//        grid.setVgap(8);
//        grid.setHgap(10);

        Group root = new Group();

        //Create the translate button
        Button translate = new Button("Translate");
//        GridPane.setConstraints(translate, 0, 0);
        translate.setOnAction(event -> Translator.translateAutomata(new ArrayList<>(stateList.values())));

        //Create the run button and input segment
        Button run = new Button("Run");
//        GridPane.setConstraints(run, 1, 0);


        TextField input = new TextField();
//        GridPane.setConstraints(input, 2, 0);
        run.setOnAction(event -> NFAInterpreter.translateNFA(input.getText(), Translator.getSigma(new ArrayList<>(stateList.values())), Translator.getQ0(new ArrayList<>(stateList.values())) , Translator.getDelta(new ArrayList<>(stateList.values())), Translator.getF(new ArrayList<>(stateList.values()))));

//        GridPane.setConstraints(root, 3, 0);

//        grid.getChildren().addAll(translate, run, input);

        EventHandler<MouseEvent> click = e -> {
            if(e.getClickCount() == 2) {
                if (onCircle(e.getX(), e.getY())) {
                    editCircle(getCircle(e.getX(), e.getY()));
                }
                else {
                    root.getChildren().add(createCircle(e.getX(), e.getY()));
                }
            }
        };

        EventHandler<MouseEvent> startDrag = e -> {
            if(onCircle(e.getX(), e.getY())){
                Circle c = getCircle(e.getX(), e.getY());
                currentLine = new Line(c.getCenterX(), c.getCenterY(), c.getCenterX(), c.getCenterY());

                root.getChildren().add(currentLine);
            }
        };

        EventHandler<MouseEvent> endDrag = e -> {
            if(onCircle(e.getX(), e.getY())){
                Circle c = getCircle(e.getX(), e.getY());
                Line newLine = new Line(currentLine.getStartX(), currentLine.getStartY(), c.getCenterX(), c.getCenterY());
                root.getChildren().remove(currentLine);
                root.getChildren().add(newLine);
            }

            else {
                root.getChildren().remove(currentLine);
            }

            currentLine = null;
        };

        EventHandler<MouseEvent> dragMouse = e -> {
            if(currentLine != null){
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
            }
        };


        primaryStage.addEventFilter(MouseEvent.MOUSE_CLICKED, click);
        primaryStage.addEventFilter(MouseEvent.MOUSE_PRESSED, startDrag);
        primaryStage.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragMouse);
        primaryStage.addEventFilter(MouseEvent.MOUSE_RELEASED, endDrag);

        primaryStage.setTitle("Automata Visualizer");
        primaryStage.setScene(new Scene(root, 500, 500));


        primaryStage.show();
    }

    public static void main(String args){
        launch(args);
    }
}
