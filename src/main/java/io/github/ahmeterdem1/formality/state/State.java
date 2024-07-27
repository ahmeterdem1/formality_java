package io.github.ahmeterdem1.formality.state;

import java.util.Map;
import java.util.HashMap;

public class State {
    protected Map<String, FiniteState> transitions;
    protected boolean FINAL;

    public State() {
        this.transitions = new HashMap<String, FiniteState>();
        this.FINAL = false;
    }

    public State(boolean flag) {
        this.transitions = new HashMap<String, FiniteState>();
        this.FINAL = flag;
    }

    /**
     * Checks if the given State is marked as a "final" state.
     *
     * @return true if final, false otherwise
     */
    public boolean isFinal() {
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
}
