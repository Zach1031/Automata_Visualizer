import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    HashMap<Circle, State> stateList = new HashMap<>();
    Stage editWindow;

    public boolean onCircle(double x, double y){
        for(Circle c : stateList.keySet()){
            if(c.contains(new Point2D(x, y))){
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
            if((c.getCenterX() == x) && (c.getCenterY() == y)){
                return c;
            }
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


    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<State> test = new ArrayList<>();

        State state1 = new State();
        State state2 = new State();

        state1.setName("q1");
        state2.setName("q2");


        state1.addTransition("0", state2);
        state2.addTransition("1", state1);

        state2.setFinal(true);

        test.add(state1);
        test.add(state2);

        System.out.println(Translator.translateAutomata(test));

        Group root = new Group();

        EventHandler<MouseEvent> click = e -> {
            if(onCircle(e.getX(), e.getY())){
                editCircle(getCircle(e.getX(), e.getY()));
            }

            else{
                root.getChildren().add(createCircle(e.getX(), e.getY()));
            }
        };


        primaryStage.addEventFilter(MouseEvent.MOUSE_CLICKED, click);

        primaryStage.setTitle("Automata Visualizer");
        primaryStage.setScene(new Scene(root, 500, 500));


        primaryStage.show();
    }

    public static void main(String args){
        launch(args);
    }
}
