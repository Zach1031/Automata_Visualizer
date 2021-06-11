import java.util.ArrayList;

public class NFAInterpreter {
    private static ArrayList<State> epsilonTransitions(ArrayList<Transition> delta, State s){
        ArrayList<State> epsilonTransitions = new ArrayList<>();

        for(Transition t : delta){
            if(t.getInput().equals("ε") && t.getStartingState().equals(s)){
                epsilonTransitions.addAll(t.getOutputStates());
            }
        }

        return epsilonTransitions;
    }

    private static ArrayList<State> nonEpsilonTransition(ArrayList<Transition> delta, State s, String input){
        ArrayList<State> epsilonTransitions = new ArrayList<>();

        for(Transition t : delta){
            if(t.getInput().equals(input) && t.getStartingState().equals(s)){
                epsilonTransitions.addAll(t.getOutputStates());
            }
        }

        return epsilonTransitions;
    }

    private static boolean goodDelta(State s, String input, Transition t){
        return (s.equals(t.getStartingState())) && (t.getInput().equals(input) || input.equals("ε"));
    }

    private static boolean finalState(ArrayList<State> q0, ArrayList<State> F){
        for(State s : q0){
            for(State finalState : F){
                if(finalState.equals(s)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean translateNFA(String input, ArrayList<String> Sigma, ArrayList<State> q0, ArrayList<Transition> delta, ArrayList<State> F, int depth){
        if(q0.size() == 0) {return false;}
        if(depth >= 50) { return false;}
        if(input.equals("")){
            return finalState(q0, F);
        }

        if(!Sigma.contains(input.substring(0,1))){
            System.out.println("INCORRECT INPUT");
            return false;
        }

        for(State s : q0){
            ArrayList<State> epsilons = epsilonTransitions(delta, s);
            ArrayList<State> nonEpsilons = nonEpsilonTransition(delta, s, input.substring(0,1));

            if(epsilons.size() == 0){
                return translateNFA(input.substring(1), Sigma, nonEpsilons, delta, F, ++depth);
            }

            else if(nonEpsilons.size() == 0){
                translateNFA(input, Sigma, epsilons, delta, F, ++depth);
            }

            return translateNFA(input, Sigma, epsilonTransitions(delta, s), delta, F, ++depth) || translateNFA(input.substring(1), Sigma, nonEpsilonTransition(delta, s, input.substring(0,1)), delta, F, ++depth);
        }

        return false;
    }
}
