package io.github.ahmeterdem1.formality.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The state class for finite automatas. It doesn't matter
 * that the automata is deterministic or not.
 */
public class FiniteState extends State {

    private boolean traversed = false;

    public FiniteState() {
        super();
    }

    public FiniteState(boolean flag) {
        super(flag);
    }

    /**
     * Add a transition rule to the state, where the match rule is
     * a char primitive. The char will be interpreted as a String.
     *
     * @param c char primitive as the transition rule
     * @param s State that the transition points to
     */
    public void addTransition(char c, FiniteState s) {
        char[] array = {c};
        this.transitions.put(new String(array), s);
    }

    /**
     * Add a transition rule to the state, where the match rule is
     * a char array. The array will be interpreted as a String.
     *
     * @param c char array as the transition rule
     * @param s State that the transition points to
     */
    public void addTransition(char[] c, FiniteState s) {
        this.transitions.put(new String(c), s);
    }

    /**
     * Add a transition rule to the state, where the match rule is
     * a Character object. The Character will be interpreted as a
     * String
     *
     * @param c Character as the transition rule
     * @param s State that the transition points to
     */
    public void addTransition(Character c, FiniteState s) {
        this.transitions.put(Character.toString(c), s);
    }

    /**
     * Add a transition rule to the state, where the match rule is
     * a String.
     *
     * @param str String as the transition rule
     * @param s State that the transition points to
     */
    public void addTransition(String str, FiniteState s) {
        this.transitions.put(str, s);
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a char primitive. The char will be interpreted as a String.
     *
     * @param c char primitive as the transition rule
     */
    public void removeTransition(char c) {
        char[] array = {c};
        this.transitions.remove(new String(array));
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a char array. The char array will be interpreted as a String.
     *
     * @param c char array as the transition rule
     */
    public void removeTransition(char[] c) {
        this.transitions.remove(new String(c));
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a Character object. The Character object will be interpreted as a
     * String.
     *
     * @param c Character object as the transition rule
     */
    public void removeTransition(Character c) {

        this.transitions.remove(Character.toString(c));
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a String.
     *
     * @param str String as the transition rule
     */
    public void removeTransition(String str) {
        this.transitions.remove(str);
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a char primitive
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(char c) {
        char[] array = {c};
        String match = this.getMatch(new String(array));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }
        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a char array
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(char[] c) {
        String match = this.getMatch(new String(c));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a Character object
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(Character c) {
        String match = this.getMatch(Character.toString(c));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param str Transition rule to match, as a String
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(String str) {
        String match = this.getMatch(str);
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Merges all the transitions of the given state, with
     * itself. After "merging", any transition pointing from
     * a to b, will start to point from a to a with the same rule
     * as before. Does not modify the given state, copies it.
     *
     * @param s1 The state to merge
     * @return Merged state
     */
    public static FiniteState merge(FiniteState s1) {
        Map<String, State> transitions = s1.getTransitions();
        FiniteState result = new FiniteState();
        for (String str : transitions.keySet()) {
            result.addTransition(str, result);
        }
        return result;
    }

    /**
     * Copies all the transitions of the first given state,
     * applies "merge" based on the obtained tranisitions
     * to the second state. Does not change both of the states,
     * works on copies. For more information, see
     * io.github.ahmeterdem1.formality.state.FiniteState.merge.
     *
     *
     * @param s1 The state to copy the transitions of
     * @param s2 The state to apply "merge" with the transition set
     * @return
     */
    public static FiniteState copy(FiniteState s1, FiniteState s2) {
        Map<String, State> transitions = s1.getTransitions();
        for (String str : transitions.keySet()) {
            s2.addTransition(str, s2);
        }
        return s2;
    }

}
