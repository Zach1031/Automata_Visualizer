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
public class Translator {


    //I should probably organize this a bit
    public static String translateAutomata(HashMap<LineTransition, String> transitionList, HashMap<Circle, State> stateList){
        ArrayList<String> sigma = getSigma(new ArrayList<>(transitionList.values()));
        ArrayList<String> Q = getQ(new ArrayList<>(stateList.values()));
        State q0 = getQ0(new ArrayList<>(stateList.values()));
        ArrayList<Transition> delta = lineTransitionToTransition(transitionList, stateList);
        ArrayList<State> finalState = getF(new ArrayList<>(stateList.values()));

        return machineToString(sigma, Q, q0, delta, finalState);
    }

    public static ArrayList<Transition> lineTransitionToTransition (HashMap<LineTransition, String> transitionList, HashMap<Circle, State> stateList){
        ArrayList<Transition> delta = new ArrayList<>();

        for(LineTransition lineTransition : transitionList.keySet()){
            Circle startCircle = lineTransition.getStartState();
            Circle endCircle = lineTransition.getEndState();
            State state1 = stateList.get(startCircle);
            State state2 = stateList.get(endCircle);
            String input = transitionList.get(lineTransition);

            delta.add(new Transition(state1, input, state2));
        }

        return delta;
    }

    public static ArrayList<Transition> translateDelta(State s){
        ArrayList<Transition> deltas = new ArrayList<>();
        HashMap<String, ArrayList<State>> transitions = s.getTransitions();

        for(String input : transitions.keySet()){
            deltas.add(new Transition(s, input, transitions.get(input)));
        }


        return deltas;
    }

    public static String machineToString(ArrayList<String> sigma, ArrayList<String> Q, State q0, ArrayList<Transition> delta, ArrayList<State> finalState){
        String returnString = "Σ: " + sigma + "\n";
        returnString += "Q: " + Q + "\n";
        returnString += "q0: " + q0 + "\n";
        returnString += "Δ: " + delta + "\n";
        returnString += "F: " + finalState + "\n";

        return returnString;
    }


    public static ArrayList<String> getSigma(ArrayList<String> transitions){
        ArrayList<String> sigma = new ArrayList<>();

        for(String s : transitions){
            if(!sigma.contains(s) && !s.equals("ε")){
                sigma.add(s);
            }
        }

        return sigma;
    }

    public static ArrayList<Transition> getDelta(ArrayList<State> stateList){
        ArrayList<Transition> delta = new ArrayList<>();
        for(State s : stateList){
            delta.addAll(translateDelta(s));
        }

        return delta;
    }

    public static ArrayList<String> getQ(ArrayList<State> stateList){
        ArrayList<String> Q = new ArrayList<>();

        for(State s : stateList){
            Q.add(s.getName());
        }

        return Q;
    }

    public static ArrayList<State> getF(ArrayList<State> stateList){
        ArrayList<State> F = new ArrayList<>();

        for(State s : stateList){
            if(s.isFinal()){
                F.add(s);
            }
        }

        return F;
    }

    public static State getQ0(ArrayList<State> stateList){
        if(stateList.size() == 0){
            return new State();
        }

        for(State s : stateList){
            if(s.isStart()){ return s;}
        }

        return stateList.get(0);
    }

}
