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
import javafx.scene.control.CheckBox;
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
    HashMap<LineTransition, String> transitionList = new HashMap<>();

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

    public boolean onLine(double x, double y){
        ArrayList<Line> lineList = new ArrayList<>();
        for(LineTransition lineTransition : transitionList.keySet()){
            lineList.add(lineTransition.getLine());
        }

        for(Line l : lineList){
            if(l.contains(new Point2D(x, y))){
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
        }

        return new Circle();
    }

    public Line getLine(double x, double y){
        ArrayList<Line> lineList = new ArrayList<>();
        for(LineTransition lineTransition : transitionList.keySet()){
            lineList.add(lineTransition.getLine());
        }

        for(Line l : lineList){
            if(l.contains(new Point2D(x, y))){
                return l;
            }
        }

        return new Line();
    }

    public void editCircle(Circle circle){
        State state = new State();

        for(Circle c : stateList.keySet()){
            if(c.equals(circle)){
                state = stateList.get(c);
            }
        }

        editWindow = new Stage();
        Label nameLabel = new Label("Name:");
        TextField nameValue = new TextField(state.getName());

        Label finalLabel = new Label("Final?");
        CheckBox finalValue = new CheckBox();
        finalValue.setSelected(state.isFinal());

        Label startLabel = new Label("Start?");
        CheckBox startValue = new CheckBox();
        startValue.setSelected(state.isStart());

        Button save = new Button("Save");
        save.setOnAction(e -> {
            State newState = new State();
            newState.setName(nameValue.getText());
            newState.setFinal(finalValue.isSelected());
            newState.setStart(startValue.isSelected());
            stateList.replace(circle, newState);

            editWindow.close();
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameValue, 1, 0);
        grid.add(startLabel, 0, 1);
        grid.add(startValue, 1, 1);
        grid.add(finalLabel, 0 , 2 );
        grid.add(finalValue, 1, 2);
        grid.add(save, 10, 10);


        editWindow.setScene(new Scene(grid, 500, 300));
        editWindow.show();
    }

    public LineTransition getTransitionFromLine(Line l){
        for(LineTransition lineTransition : transitionList.keySet()){
            if(lineTransition.getLine().equals(l)){
                return lineTransition;
            }
        }

        return new LineTransition();
    }

    public void editLine(Line line){
        LineTransition lineTransition = getTransitionFromLine(line);

        editWindow = new Stage();
        Label inputLabel = new Label("Input:");
        TextField inputValue = new TextField(transitionList.get(lineTransition));

        Label epsilonLabel = new Label("Epsilon?");
        CheckBox epsilonValue = new CheckBox();
        epsilonValue.setSelected(transitionList.get(lineTransition).equals("ε"));


        Button save = new Button("Save");
        save.setOnAction(e -> {
            Transition newTransition = new Transition();
            String input = "";

            if (epsilonValue.isSelected()) {
                input = "ε";
            } else {
                input = inputValue.getText();
            }

            transitionList.replace(lineTransition, input);

            editWindow.close();
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(inputLabel, 0, 0);
        grid.add(inputValue, 1, 0);
        grid.add(epsilonLabel, 0 , 2 );
        grid.add(epsilonValue, 1, 2);
        grid.add(save, 10, 10);


        editWindow.setScene(new Scene(grid, 500, 300));
        editWindow.show();
    }

    //THE FORMATING IS NOT CORRECT
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Group root = new Group();


        //Create the translate button
        Button translate = new Button("Translate");
        GridPane.setConstraints(translate, 0, 0);
        translate.setOnAction(e -> System.out.println(Translator.translateAutomata(transitionList, stateList)));

        //Create the run button and input segment
        Button run = new Button("Run");
        GridPane.setConstraints(run, 1, 0);


        TextField input = new TextField();
        GridPane.setConstraints(input, 2, 0);
        run.setOnAction(event -> System.out.println(NFAInterpreter.translateNFA(input.getText(), transitionList, stateList)));


        GridPane.setConstraints(root, 3, 0);

        grid.getChildren().addAll(translate, run, input);
        root.getChildren().add(grid);


        EventHandler<MouseEvent> click = e -> {
            if(e.getClickCount() == 2) {
                if (onCircle(e.getX(), e.getY())) {
                    editCircle(getCircle(e.getX(), e.getY()));
                }

                else if(onLine(e.getX(), e.getY())){
                    editLine(getLine(e.getX(), e.getY()));
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
                currentLine.setStrokeWidth(8);

                root.getChildren().add(currentLine);
            }
        };
        EventHandler<MouseEvent> endDrag = e -> {
            if(onCircle(e.getX(), e.getY()) && (!getCircle(e.getX(), e.getY()).equals(getCircle(currentLine.getStartX(), currentLine.getStartY())))){
                Circle state1 = getCircle(currentLine.getStartX(), currentLine.getStartY());
                Circle state2 = getCircle(e.getX(), e.getY());
                Line newLine = new Line(currentLine.getStartX(), currentLine.getStartY(), state2.getCenterX(), state2.getCenterY());
                root.getChildren().remove(currentLine);
                newLine.setStrokeWidth(8);
                root.getChildren().add(newLine);

                transitionList.put(new LineTransition(state1, newLine, state2), "ε");
//                transitionList.put(newLine, new Transition(stateList.get(state1), "", stateList.get(state2)));
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
