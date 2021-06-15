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

public class LineTransition {
    private Circle endState;
    private Circle startState;
    private Line line;

    public LineTransition(){

    }

    public LineTransition(Circle startState, Line line, Circle endState){
        this.startState = startState;
        this.line = line;
        this.endState = endState;
    }

    public Circle getEndState() {
        return endState;
    }

    public void setEndState(Circle endState) {
        this.endState = endState;
    }

    public Circle getStartState() {
        return startState;
    }

    public void setStartState(Circle startState) {
        this.startState = startState;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
