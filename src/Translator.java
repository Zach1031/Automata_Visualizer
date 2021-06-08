import java.util.ArrayList;

public class Translator {


    public static String translateAutomata(ArrayList<State> stateList){
        ArrayList<String> sigma = new ArrayList<>();
        ArrayList<String> Q = new ArrayList<>();
        ArrayList<Transition> delta = new ArrayList<>();
        ArrayList<State> finalState = new ArrayList<>();

        for(State s : stateList){
            sigma = addToSigma(sigma, s);

            Q.add(s.getName());

            delta.add(translateDelta(s));

            if(s.isFinal()){ finalState.add(s); }
        }

        return machineToString(sigma, Q, delta, finalState);
    }

    public static Transition translateDelta(State s){
        Transition transition = new Transition();

        transition.setStartingState(s.getName());
        transition.setOutputStates();
    }

    public static String machineToString(ArrayList<String> sigma, ArrayList<String> Q, ArrayList<Transition> delta, ArrayList<State> finalState){
        String returnString = "Σ: " + sigma + "\n";
        returnString += "Q: " + Q + "\n";
        returnString += "δ: " + delta + "\n";
        returnString += "F: " + finalState + "\n";

        System.out.println("Σ: " + sigma);
        System.out.println("Q: " + Q);
        System.out.println("δ: " + delta);
        System.out.println("F: " + finalState);

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
