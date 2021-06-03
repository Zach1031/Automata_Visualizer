import javafx.scene.shape.Circle;

import java.util.HashMap;

public class State {
    private boolean isFinal;
    String name;
    HashMap<String, State> transitions;
    Circle visual;

    public State(){

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

    public void addTransition(String input, State goTo){
        transitions.put(input, goTo);
    }

    public Circle getVisual() {
        return visual;
    }

    public void setVisual(Circle visual) {
        this.visual = visual;
    }
}
