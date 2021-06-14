import java.util.ArrayList;
import java.util.HashMap;

public class Translator {


    //I should probably organize this a bit
    public static String translateAutomata(ArrayList<State> stateList){
        ArrayList<String> sigma = getSigma(stateList);
        ArrayList<String> Q = getQ(stateList);
        State q0 = getQ0(stateList);
        ArrayList<Transition> delta = getDelta(stateList);
        ArrayList<State> finalState = getF(stateList);

        return machineToString(sigma, Q, q0, delta, finalState);
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

    public static ArrayList<String> addToSigma(ArrayList<String> sigma, State s){
        for(String input : s.getInput()){
            if(!sigma.contains(input)){
                sigma.add(input);
            }
        }

        return sigma;
    }

    public static ArrayList<String> getSigma(ArrayList<State> stateList){
        ArrayList<String> sigma = new ArrayList<>();

        for(State s : stateList){
            sigma = addToSigma(sigma, s);
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
        for(State s : stateList){
            if(s.isStart()){ return s;}
        }

        return stateList.get(0);
    }

}
