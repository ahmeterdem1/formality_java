package io.github.ahmeterdem1.formality.state;

import java.util.Map;
import java.util.HashMap;

/**
 * General purpose State class to build relational state based
 * structures. Each state has its own transition rules that
 * point to other states. A graph-like structure is formed
 * with collection of several states and binding transition
 * rules.
 */
public class State {
    protected Map<String, State> transitions;
    protected boolean FINAL;

    public State() {
        this.transitions = new HashMap<String, State>();
        this.FINAL = false;
    }

    public State(boolean flag) {
        this.transitions = new HashMap<String, State>();
        this.FINAL = flag;
    }

    /**
     * Checks if the given State is marked as a "final" state.
     *
     * @return true if final, false otherwise
     */
    public final boolean isFinal() {
        return this.FINAL;
    }

    /**
     * Set "final" configuration of the State object.
     *
     * @param flag boolean value to set the "final" flag as
     */
    public void setFinal(boolean flag) {
        this.FINAL = flag;
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
    public Map<String, State> getTransitions() {
        return this.transitions;
    }

    /**
     * Get the exact State that "str" as a transition rule points to.
     *
     * @param str Transition rule to return the target State of
     * @return Returns the State if the rule exists, null otherwise
     */
    public State getState(String str) {
        if (this.transitions.containsKey(str)) {
            return this.transitions.get(str);
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

        return match;  // An empty string means no match, also it means a Lambda Transition
    }

}
