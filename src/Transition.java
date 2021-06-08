import java.util.ArrayList;

public class Transition {
    private String startingState;
    private String input;
    private ArrayList<String> outputStates;

    public Transition(){

    }

    public Transition(String startingState, String input, ArrayList<String> outputStates){
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

    public ArrayList<String> getOutputStates() {
        return outputStates;
    }

    public void setOutputStates(ArrayList<String> outputStates) {
        this.outputStates = outputStates;
    }

    @Override
    public String toString() {
        return "";
    }
}
