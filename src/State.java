import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class State {
    private boolean isFinal;
    private String name;
    private HashMap<String, ArrayList<State>> transitions = new HashMap<>();
    private Circle visual;

    public State(){
        this.name = "";
        this.isFinal = false;
        transitions = new HashMap<>();
    }

    public State(String name, boolean isFinal, int x, int y){
        this.name = name;
        this.isFinal = isFinal;
        transitions = new HashMap<>();

        visual = new Circle();
        visual.setCenterX(x);
        visual.setCenterY(y);
        visual.setRadius(25.0f);
        visual.setStrokeWidth(20);
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal){
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Add Transition doesn't work
    public void addTransition(String input, State goTo){
        ArrayList<State> transitionStates = new ArrayList<>();

        if(transitions.containsKey(input)){
            transitionStates.addAll(transitions.get(input));

            transitionStates.add(goTo);
            transitions.remove(input);
        }

        transitionStates.add(goTo);
        transitions.put(input,transitionStates);
    }

    public HashMap<String, ArrayList<State>> getTransitions(){
        return transitions;
    }

    public Circle getVisual() {
        return visual;
    }

    public void setVisual(Circle visual) {
        this.visual = visual;
    }

    public ArrayList<State> getTransitionStates(){
        ArrayList<State> transitionStates = new ArrayList<>();
        for(ArrayList<State> goToList : transitions.values()){
            transitionStates.addAll(goToList);
        }

        return transitionStates;
    }

    public ArrayList<String> getInput(){
        return new ArrayList<>(transitions.keySet());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return isFinal == state.isFinal && Objects.equals(name, state.name) && Objects.equals(transitions, state.transitions) && Objects.equals(visual, state.visual);
    }
}
