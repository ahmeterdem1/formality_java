package io.github.ahmeterdem1.formality.state;

import java.util.Map;

/**
 * General purpose State class to build relational state based
 * structures. Each state has its own transition rules that
 * point to other states. A graph-like structure is formed
 * with collection of several states and binding transition
 * rules.
 */
public class FiniteState extends State {

    public FiniteState() {
        super();
    }

    public FiniteState(boolean flag) {
        super(flag);
    }

    /**
     * Get "transitions" property of the given object. This property
     * holds the transition rules as a HashMap object, where the keys
     * are strings that matches are operated on, and the values are
     * the other states that the given transition rule points to.
     *
     *
     * @return a HashMap object of transition rules
     */
    public Map<String, FiniteState> getTransitions() {
        return this.transitions;
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
     * Get the exact State that "str" as a transition rule points to.
     *
     * @param str Transition rule to return the target State of
     * @return Returns the State if the rule exists, null otherwise
     */
    public FiniteState getState(String str) {
        if (this.transitions.containsKey(str)) {
            return this.transitions.get(str);
        }
        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a char primitive
     * @return Returns the State if the rule exists, null otherwise
     */
    public FiniteState get(char c) {
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
    public FiniteState get(char[] c) {
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
    public FiniteState get(Character c) {
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
    public FiniteState get(String str) {
        String match = this.getMatch(str);
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Get the maximum possible length matching given the transition rules.
     *
     * @param str String to try to match to rules
     * @return Returns the matching String, empty String if the rule does not exist
     */
    public String getMatch(String str) {
        String match = "";
        for (String s : this.transitions.keySet()) {
            if (str.startsWith(s) && s.length() > match.length()) {
                match = s;
            }
        }

        return match;  // An empty string means no match
    }

}
