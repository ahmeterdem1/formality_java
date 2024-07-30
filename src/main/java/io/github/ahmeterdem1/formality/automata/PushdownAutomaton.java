package io.github.ahmeterdem1.formality.automata;

import io.github.ahmeterdem1.formality.state.PushdownState;
import io.github.ahmeterdem1.formality.Alphabet;
import java.util.Stack;

/**
 * Pushdown Automaton derived from base Automaton.
 * By its formal definition, implements a stack, registered
 * with a separate alphabet.
 */
public class PushdownAutomaton extends Automaton {
    private Stack<Character> stack;
    private Alphabet stackAlphabet;

    public PushdownAutomaton() {
        super();
        this.stack = new Stack<Character>();
        this.stackAlphabet = new Alphabet();
    }

    public PushdownAutomaton(String str) {
        super(str);
        this.stack = new Stack<Character>();
        this.stackAlphabet = new Alphabet();
    }

    @Override
    public String toString() {
        return String.format("Automaton: %s\nAlphabet: %s\nStack Alphabet: %s\n",
                this.name,
                this.alphabet.toString(),
                this.stackAlphabet.toString());
    }

    /**
     * Assign the stack Alphabet of the language.
     *
     * @param a Alphabet object to assign to
     */
    public void setStackAlphabet(Alphabet a) {
        this.stackAlphabet = a;
    }

    /**
     * Set the stack Alphabet of the language.
     *
     * @param chars Array of char primitives representing the alphabet
     */
    public void setStackAlphabet(char[] chars) {
        this.stackAlphabet.setCharacters(chars);
    }

    /**
     * Set the stack Alphabet of the language.
     *
     * @param chars Array of Character's representing the alphabet
     */
    public void setStackAlphabet(Character[] chars) {
        this.stackAlphabet.setCharacters(chars);
    }

    /**
     * Get the stack Alphabet of the language.
     *
     * @return Alphabet object, of the stack
     */
    public Alphabet getStackAlphabet() {
        return this.stackAlphabet;
    }

    /**
     * Checks if the given string belongs to the represented language.
     * Always tries to match the longest possible substring to possible
     * available transition rules given the state. This method is not
     * accurate for non-deterministic automatons. As the definition of
     * Pushdown Automata, when all of the String is consumed, the state
     * must be final and the stack must be empty.
     *
     * @param str: The piece of string to be checked
     * @return Returns true if the string belongs to the language, false
     *      otherwise
     */
    @Override
    public boolean validate(String str) {

        if (this.alphabet.validate(str)) {
            String substring;
            String match;
            String stackTransition;
            String stackString;
            PushdownState next;

            match = this.begin.getMatch(str);
            next = (PushdownState) this.begin.getState(match);

            int i;

            // There cannot be a "read" transition at the beginning, since the stack is empty.
            stackTransition = ((PushdownState) this.begin).getWriteMatch(match);
            if (!stackTransition.isEmpty()) {
                for (i = 0; i < stackTransition.length(); i++) {
                    this.stack.push(stackTransition.charAt(i));
                }
            } else if (!((PushdownState) this.begin).getReadMatch(match).isEmpty()) {
                return false;
            }

            substring = str.substring(match.length(), str.length());

            while (!substring.isEmpty() && (next != null)) {
                match = next.getMatch(substring);
                stackTransition = next.getReadMatch(match);

                // Kernel
                if (!stackTransition.isEmpty()) {
                    if (this.stack.isEmpty()) return false;

                    stackString = "";
                    for (i = 0; i < stackTransition.length(); i++) {
                        stackString = this.stack.pop() + stackString;
                    }
                    if (!stackTransition.equals(stackString)) return false;
                } else {
                    stackTransition = next.getWriteMatch(match);
                    if (!stackTransition.isEmpty()) {
                        for (i = 0; i < stackTransition.length(); i++) {
                            this.stack.push(stackTransition.charAt(i));
                        }
                    }
                }

                next = (PushdownState) next.getState(match);  // Update "next"
                substring = substring.substring(match.length(), substring.length());
            }

            // Empty the stack before returning anything

            if (substring.isEmpty() && (next != null) && next.isFinal() && this.stack.isEmpty()) {
                return true;
            }

            this.stack.empty();
            return false;
        }

        // Stack is not manipulated here.
        return false;
    }
}
