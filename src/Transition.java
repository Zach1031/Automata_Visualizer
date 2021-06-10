import java.util.ArrayList;

public class Transition {
    private String startingState;
    private String input;
    private ArrayList<State> outputStates;

    public Transition(){

    }

    public Transition(String startingState, String input, ArrayList<State> outputStates){
        this.startingState = startingState;
        this.input = input;
        this.outputStates = outputStates;
    }

    public String getStartingState() {
        return startingState;
    }

    public void setStartingState(String startingState) {
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
