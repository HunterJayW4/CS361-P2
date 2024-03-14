package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface {

    private LinkedHashSet<Character> sigma;
    private NFAState startState;
    private LinkedHashSet<NFAState> finalStates;
    private LinkedHashSet<NFAState> states;
    private HashMap<String, HashMap<Character, Set<String>>> delta;

    public NFA (){
        sigma = new LinkedHashSet<Character>();
        startState = new NFAState();
        finalStates = new LinkedHashSet<NFAState>();
        states = new LinkedHashSet<NFAState>();
        delta = new HashMap<String, HashMap<Character, Set<String>>>();
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
        // Check if the state exists and if so, set it as the start state
        for (State tmp : states) {
            if (tmp.getName().equals(name)) {
                // Assuming startState should hold a single state reference
                // Directly set the found state as the start state
                startState = (NFAState) tmp;
                return true;
            }
        }
        // If no state with the given name exists, return false
        return false;
    }


    @Override
    public void addSigma(char symbol) {
        // checks if the symbol already exists in sigma. Returns if it does to prevent duplicates
        if(sigma.contains(symbol) || symbol == 'e'){
            return;
        }
        sigma.add((Character)symbol);
    }

    @Override
    public boolean accepts(String s) {
        // Start from the e-closure of the start state
        Set<NFAState> currentStates = eClosure(startState);

        // Iterate over each character in the string
        for (char c : s.toCharArray()) {
            Set<NFAState> nextStates = new HashSet<>();

            // Move to next states based on the current character
            for (NFAState state : currentStates) {
                Set<NFAState> statesForChar = getToState(state, c);
                if (statesForChar != null) {
                    for (NFAState nextState : statesForChar) {
                        nextStates.addAll(eClosure(nextState)); // Include e-closure of next states
                    }
                }
            }

            // Update current states to the next states
            currentStates = nextStates;
        }

        // Check if any of the current states is a final state
        for (NFAState state : currentStates) {
            if (isFinal(state.getName())) {
                return true; // Accept if any final state is reached
            }
        }

        return false; // Reject if no final state is reached
    }



    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public NFAState getState(String name) {
        // Directly iterate through the states set to find the state with the given name
        for (State tmp : states) {
            if (tmp.getName().equals(name)) {
                // Return the matching state as NFAState
                return (NFAState) tmp;
            }
        }
        // Return null if no state with the given name exists
        return null;
    }


    @Override
    public boolean isFinal(String name) {
        // Directly iterate through the finalStates set to check if any state matches the given name
        for (State tmp : finalStates) {
            if (tmp.getName().equals(name)) {
                return true; // Return true if a matching state is found
            }
        }
        return false; // Return false if no matching state is found
    }


    @Override
    public boolean isStart(String name) {
        // returns true if param is the start state
        if(startState.getName().equals(name)){
            return true;
        }
        return false;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> result = new HashSet<>();

        // Assume delta is structured correctly and contains transitions
        if (delta.containsKey(from.getName()) && delta.get(from.getName()).containsKey(onSymb)) {
            Set<String> targetStateNames = delta.get(from.getName()).get(onSymb);
            for (String stateName : targetStateNames) {
                NFAState state = getState(stateName);
                if (state != null) {
                    result.add(state);
                }
            }
        }
        return result;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();

        stack.push(s);
        closure.add(s);

        while (!stack.isEmpty()) {
            NFAState currentState = stack.pop();

            // Assuming delta is structured to support NFA: String -> (Character -> Set<String>)
            // And assuming a method to convert state names to NFAState objects exists
            if (delta.containsKey(currentState.getName()) && delta.get(currentState.getName()).containsKey('e')) {
                Set<String> nextStateNames = delta.get(currentState.getName()).get('e');
                for (String nextStateName : nextStateNames) {
                    NFAState nextState = getState(nextStateName); // Convert state name to NFAState
                    if (!closure.contains(nextState)) {
                        closure.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }

        return closure;
    }

    @Override
    public int maxCopies(String s) {
        // Start with the initial state and its epsilon closure as the current states.
        Set<NFAState> currentStates = new HashSet<>(eClosure(startState));
        int maxCopies = currentStates.size();

        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);
            Set<NFAState> nextStates = new HashSet<>();

            for (NFAState state : currentStates) {
                // For each state, find where you can go with the current symbol
                Set<NFAState> transitions = getToState(state, symbol);
                if (transitions != null) {
                    for (NFAState nextState : transitions) {
                        // Add all states reachable from nextState through epsilon closure
                        nextStates.addAll(eClosure(nextState));
                    }
                }
            }

            // Update current states to be the next set of states
            currentStates = nextStates;
            // Update maxCopies if the number of states in this step is greater
            maxCopies = Math.max(maxCopies, currentStates.size());
        }

        // Return the maximum number of NFA "copies" encountered, interpreted as the max breadth of parallel active states.
        return maxCopies;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        // Check if fromState exists in states
        boolean fromStateExists = states.stream().anyMatch(s -> s.getName().equals(fromState));

        // Check if all toStates exist in states
        boolean allToStatesExist = toStates.stream().allMatch(ts -> states.stream().anyMatch(s -> s.getName().equals(ts)));

        // Check if onSymb is valid (assuming 'e' is epsilon and sigma is a Set<Character> of valid symbols)
        boolean isValidSymbol = sigma.contains(onSymb) || onSymb == 'e';

        if(fromStateExists && allToStatesExist && isValidSymbol) {
            // Ensure the delta map for fromState exists
            delta.putIfAbsent(fromState, new HashMap<>());

            // Update the transition for onSymb to include all toStates. Assuming you want to support multiple toStates for a given fromState and symbol.
            // Note: This line might need adjustment based on how you intend to store multiple toStates for the same fromState and onSymb.
            // If you want to merge with existing states, you'll need to get the current set and add all toStates.
            Set<String> currentToStates = delta.get(fromState).getOrDefault(onSymb, new HashSet<>());
            currentToStates.addAll(toStates);
            delta.get(fromState).put(onSymb, currentToStates);

            return true;
        }
        return false;
    }


    @Override
    public boolean isDFA() {
        // Check for ε (epsilon) transitions; their presence means this is not a DFA
        for (Map.Entry<String, HashMap<Character, Set<String>>> stateTransitions : delta.entrySet()) {
            if (stateTransitions.getValue().containsKey('e')) {
                return false;
            }
        }

        // Check each state's transitions to ensure determinism: exactly one transition per input symbol
        for (Map.Entry<String, HashMap<Character, Set<String>>> stateTransitions : delta.entrySet()) {
            HashMap<Character, Set<String>> transitions = stateTransitions.getValue();

            // Check if there's exactly one transition for each symbol in the alphabet
            for (Character symbol : sigma) {
                Set<String> toStates = transitions.get(symbol);

                // If any symbol does not have exactly one transition, or the transition is missing, it's not a DFA
                if (toStates == null || toStates.size() != 1) {
                    return false;
                }
            }
        }

        // If no ε transitions and exactly one transition per symbol per state, it's a DFA
        return true;
    }

}
