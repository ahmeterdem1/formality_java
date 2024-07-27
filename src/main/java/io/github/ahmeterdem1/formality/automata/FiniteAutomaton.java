package io.github.ahmeterdem1.formality.automata;

import io.github.ahmeterdem1.formality.state.FiniteState;

/**
 * A deterministic finite automaton implementation.
 * Only holds the initial State object in itself.
 * Other states that are connected to the initial
 * states are reached via graph-like structure of
 * the State objects' interface.
 *
 */
public class FiniteAutomaton extends Automaton {

    public FiniteAutomaton() {
        super();
    }

    public FiniteAutomaton(String n) {
        super(n);
    }

    /**
     * Checks if the given string belongs to the represented language.
     * Always tries to match the longest possible substring to possible
     * available transition rules given the state. This method is not
     * accurate for non-deterministic automatons.
     *
     * @param str: The piece of string to be checked
     * @return Returns true if the string belongs to the language, false
     *      otherwise
     */
    public boolean validate(String str) {

        if (this.alphabet.validate(str)) {
            String substring;
            String match;
            FiniteState next;

            match = this.begin.getMatch(str);
            next = this.begin.getState(match);
            substring = str.substring(match.length(), str.length());

            while (!substring.isEmpty() && (next != null)) {
                match = next.getMatch(substring);
                next = next.getState(match);  // Update "next"
                substring = substring.substring(match.length(), substring.length());
            }

            if (substring.isEmpty() && (next != null) && next.isFinal()) {
                return true;
            }

            return false;
        }

        return false;
    }
}
