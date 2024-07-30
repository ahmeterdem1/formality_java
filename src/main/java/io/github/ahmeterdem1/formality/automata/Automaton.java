package io.github.ahmeterdem1.formality.automata;

import io.github.ahmeterdem1.formality.Alphabet;
import io.github.ahmeterdem1.formality.state.State;

/**
 * Base class for all types of Automatons. Getter and
 * setter methods are implemented at this level.
 */
public class Automaton {
    protected Alphabet alphabet;
    protected State begin;
    protected String name;

    public Automaton() {
        this.alphabet = new Alphabet();
        this.begin = new State();
        this.name = "";
    }

    public Automaton(String str) {
        this.alphabet = new Alphabet();
        this.begin = new State();
        this.name = str;
    }

    /**
     * Assign the Alphabet of the language.
     *
     * @param a Alphabet object to assign to
     */
    public void setAlphabet(Alphabet a) {
        this.alphabet = a;
    }

    /**
     * Set the Alphabet of the language.
     *
     * @param chars Array of char primitives representing the alphabet
     */
    public void setAlphabet(char[] chars) {
        this.alphabet.setCharacters(chars);
    }

    /**
     * Set the Alphabet of the language.
     *
     * @param chars Array of Character objects representing the alphabet
     */
    public void setAlphabet(Character[] chars) {
        this.alphabet.setCharacters(chars);
    }

    /**
     * Getter for the alphabet of the represented language.
     *
     * @return Alphabet object, of the language
     */
    public Alphabet getAlphabet() {
        return this.alphabet;
    }

    /**
     * Sets the begin state of the Automaton.
     *
     * @param s State object that will be the initial state
     */
    public void setBegin(State s) {
        this.begin = s;
    }

    /**
     * Reset the properties of the automaton.
     */
    public void clearAutomaton() {
        this.alphabet = new Alphabet();
        this.begin = new State();
    }

    public String toString() {
        return String.format("Automaton: %s\nAlphabet: %s\n",
                this.name,
                this.alphabet.toString());
    }

    public boolean validate(String str) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
