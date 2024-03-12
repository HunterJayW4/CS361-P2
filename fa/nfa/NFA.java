package fa.nfa;

import java.util.*;
import java.util.stream.Collectors;

import fa.State;

public class NFA implements NFAInterface {

    private Set<State> states;
    private Set<State> finalStates;
    private State startState;
    private Set<Character> sigma;
    private Map<State, Map<Character, Set<State>>> transitionFunction;

    public NFA() {
        this.states = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
        this.sigma = new LinkedHashSet<>();
        this.transitionFunction = new HashMap<>();
    }

    @Override
    public boolean addState(String name) {
        // Check if the state already exists
        for (State tmp : states) {
            if (tmp.getName().contains(name)) {
                return false;
            }
        }
        // If the state does not exist, add it to the set
        states.add(new NFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        // Check if the state is already a final state.
        for (State tmp : finalStates) {
            if (tmp.getName().equals(name)) {
                return false;
            }
        }

        // Check if the state exists
        for (State tmp : states) {
            if (tmp.getName().equals(name)) {
                finalStates.add(getState(name));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        // Check if the state exists.
        for (State tmp : states) {
            if (tmp.getName().equals(name)) {
                startState = tmp;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        for(State state : states) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(getState(name));
    }

    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // Get the transitions map for the 'from' state
        Map<Character, Set<NFAState>> transitions = transitionFunction.get(from);
        if (transitions == null) {
            // If there are no transitions from the 'from' state, return an empty set
            return new HashSet<>();
        }
        // Get the set of states that can be reached on 'onSymb'
        Set<NFAState> toStates = transitions.get(onSymb);
        if (toStates == null) {
            // If there are no states that can be reached on 'onSymb', return an empty set
            return new HashSet<>();
        }
        // Return the set of states that can be reached on 'onSymb'
        return toStates;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eClosure'");
    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        // Check if the 'fromState' exists and the symbol is in the alphabet
        if (getState(fromState) == null || !sigma.contains(onSymb)) {
            return false;
        }
        // Check if all 'toStates' exist
        for (String toState : toStates) {
            if (getState(toState) == null) {
                return false;
            }
        }
        // Get the transitions map for the 'fromState'
        Map<Character, Set<State>> transitions = transitionFunction.get(getState(fromState));
        if (transitions == null) {
            // If there are no transitions from the 'fromState', create a new map
            transitions = new HashMap<>();
            transitionFunction.put(getState(fromState), transitions);
        }
        // Get the set of states that can be reached on 'onSymb'
        Set<State> onSymbStates = transitions.get(onSymb);
        if (onSymbStates == null) {
            // If there are no states that can be reached on 'onSymb', create a new set
            onSymbStates = new HashSet<>();
            transitions.put(onSymb, onSymbStates);
        }
        // Add all 'toStates' to the set of states that can be reached on 'onSymb'
        for (String toState : toStates) {
            onSymbStates.add(getState(toState));
        }
        return true;
    }

    @Override
    public boolean isDFA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDFA'");
    }
}
