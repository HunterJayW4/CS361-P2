package fa.nfa;

import fa.State;

/**
 * A state class for NFAs.
 * Needed for the NFAInterface.
 * 
 * @author Hunter Walp & Karter Melad
 * @version Spring 2024
 */
public class NFAState extends State {

    public NFAState(){
        super("");
    }

    public NFAState(String name){
        super(name);
    }
}
