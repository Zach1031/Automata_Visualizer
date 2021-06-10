import java.util.ArrayList;
import java.util.HashMap;

public class Translator {


    public static String translateAutomata(ArrayList<State> stateList){
        ArrayList<String> sigma = new ArrayList<>();
        ArrayList<String> Q = new ArrayList<>();
        ArrayList<Transition> delta = new ArrayList<>();
        ArrayList<State> finalState = new ArrayList<>();

        for(State s : stateList){
            sigma = addToSigma(sigma, s);

            Q.add(s.getName());

            delta.addAll(translateDelta(s));

            if(s.isFinal()){ finalState.add(s); }
        }

        return machineToString(sigma, Q, delta, finalState);
    }

    public static ArrayList<Transition> translateDelta(State s){
        ArrayList<Transition> deltas = new ArrayList<>();
        HashMap<String, ArrayList<State>> transitions = s.getTransitions();

        for(String input : transitions.keySet()){
            deltas.add(new Transition(s.getName(), input, transitions.get(input)));
        }


        return deltas;
    }

    public static String machineToString(ArrayList<String> sigma, ArrayList<String> Q, ArrayList<Transition> delta, ArrayList<State> finalState){
        String returnString = "Σ: " + sigma + "\n";
        returnString += "Q: " + Q + "\n";
        returnString += "Δ: " + delta + "\n";
        returnString += "F: " + finalState + "\n";

        return returnString;
    }

    public static ArrayList<String> addToSigma(ArrayList<String> sigma, State s){
        for(String input : s.getInput()){
            if(!sigma.contains(input)){
                sigma.add(input);
            }
        }

        return sigma;
    }

}
