import java.util.ArrayList;

public class Transition {
    private State startingState;
    private String input;
    private ArrayList<State> outputStates;

    public Transition(){
        this.startingState = new State();
        this.input = "ε";
        this.outputStates = new ArrayList<>();
    }

    public Transition(State startingState, String input, ArrayList<State> outputStates){
        this.startingState = startingState;
        this.input = input;
        this.outputStates = outputStates;
    }

    public Transition(State startingState, String input, State outputState){
        this.startingState = startingState;
        this.input = input;
        ArrayList<State> outputStates = new ArrayList<>();
        outputStates.add(outputState);
        this.outputStates = outputStates;
    }

    public State getStartingState() {
        return startingState;
    }

    public void setStartingState(State startingState) {
        this.startingState = startingState;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList<State> getOutputStates() {
        return outputStates;
    }

    public void setOutputStates(ArrayList<State> outputStates) {
        this.outputStates = outputStates;
    }

    @Override
    public String toString() {
        return String.format("δ(%s, %s, " + outputStates + ")", startingState, input) ;
    }
}
