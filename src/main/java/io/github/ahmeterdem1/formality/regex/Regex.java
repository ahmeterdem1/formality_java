package io.github.ahmeterdem1.formality.regex;

import io.github.ahmeterdem1.formality.regex.Match;
import io.github.ahmeterdem1.formality.Alphabet;
import io.github.ahmeterdem1.formality.state.FiniteState;
import io.github.ahmeterdem1.formality.automata.FiniteAutomaton;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This is a static class that collects all methods
 * related to regular expressions. The class itself
 * is not initializable.
 */
public class Regex {

    /**
     * Compile a regular expression, into a FiniteAutomata.
     * The returned automata is not always guaranteed to be
     * a DFA. The compiler uses lambda transitions to simulate
     * + and * loops.
     *
     * @param str Regex string to compile
     * @return FiniteAutomaton representing the regex
     */
    public static FiniteAutomaton compile(String str) {
        FiniteAutomaton result = new FiniteAutomaton();
        Alphabet alphabet = new Alphabet();
        FiniteState begin = new FiniteState();

        FiniteState end = Regex.build(str, begin);
        end.setFinal(true);

        result.setBegin(begin);
        alphabet.setCharacters(str);
        result.setAlphabet(alphabet);


        return result;
    }

    /**
     * Find regex matches in a text. Tries every possible
     * substring of the given text. Built based on regex
     * compiler which is in the same class, and validation
     * method of the FiniteAutomata class, "validate". See
     * related documentation for more insight on those.
     *
     * @param str The text to perform pattern search on
     * @param regex The regex string to compile
     * @return The ArrayList of all possible matches, as Match objects
     */
    public static ArrayList<Match> match(String str, String regex) {
        ArrayList<Match> result = new ArrayList<Match>();
        FiniteAutomaton machine = Regex.compile(regex);
        int length = str.length();
        String substr;

        // Below implementation is probably suboptimal, very poor indeed.
        // TODO: Renew the algorithm such that it is "length agnostic".

        // Sub O(length^2) at least
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length + 1; j++) {
                substr = str.substring(i, j);
                if (machine.validate(substr)) {
                    result.add(new Match(substr, i));
                }
            }
        }

        return result;
    }

    /**
     * Sequential FiniteAutomaton builder for regex strings.
     * Parses the string character by charatcer, or clause by
     * clause if encountered, then connects each piece with
     * their respective transition rules sequentially.
     *
     * @param str The regex string to parse
     * @param begin The beginning state
     * @return The last(deepest) state that the built graph reaches
     */
    private static FiniteState build(String str, FiniteState begin) {
        FiniteState nextState, lastState, previousState;
        Stack<Character> charStack = new Stack<Character>();
        char lastCharacter = 0;
        char c;
        int i, j, length = str.length();
        lastState = begin;
        previousState = begin;

        for (i = 0; i < length; i++) {
            c = str.charAt(i);

            switch (c) {
                case '(':
                    charStack.push('(');
                    j = i + 1;
                    nextState = Regex.build(str.substring(j), lastState);
                    // After the call to this, there is a connected subgraph
                    // from lastState to nextState
                    i = Regex.moveCursor(str, j, charStack);
                    charStack.empty();
                    // Above i points to the index after the last possible ) or ]
                    if (i == -1) return null;
                    if (i < length) {
                        c = str.charAt(i);
                        if (c == '+') {
                            nextState.addTransition("", lastState);
                        } else if (c == '*') {
                            nextState.addTransition("", lastState);
                            lastState.addTransition("", nextState);
                        } else {  // This is basically the default case
                            lastState = nextState;  // Refresh
                            nextState = new FiniteState();
                            lastState.addTransition(c, nextState);
                        }
                    }

                    previousState = lastState;
                    lastState = nextState;

                    break;
                case ')':  // Topmost .build call won't return from this case
                    if (!charStack.isEmpty() && charStack.peek() == '(') charStack.pop();

                    if (charStack.isEmpty()) return lastState;
                    else return null;

                case '[':
                    charStack.push('[');
                    j = i + 1;
                    nextState = Regex.orBuild(str.substring(j), lastState);
                    i = Regex.moveCursor(str, j, charStack);
                    charStack.empty();
                    // Above i points to the index after the last possible ) or ]
                    if (i == -1) return null;
                    if (i < length) {
                        c = str.charAt(i);
                        if (c == '+') {
                            // This is not so optimized
                            nextState.addTransition("", begin);
                            lastState.addTransition("", nextState);
                            //lastState = nextState;
                        } else if (c == '*') {
                            nextState.addTransition("", begin);
                            begin.addTransition("", lastState);
                        } else {
                            lastState = nextState;  // Refresh
                            nextState = new FiniteState();
                            lastState.addTransition(c, nextState);
                        }
                    }
                    previousState = lastState;
                    lastState = nextState;
                    break;
                case ']': // Topmost .build call won't return from this case

                    if (!charStack.isEmpty() && charStack.peek() == '[') charStack.pop();

                    if (charStack.isEmpty()) return lastState;
                    else return null;

                case '+':
                    lastState.addTransition(lastCharacter, lastState);
                    break;
                case '*':
                    previousState.addTransition("", lastState);
                    lastState.addTransition(lastCharacter, lastState);
                    break;
                case '\\':
                    i++;
                    if (i < length) {
                        c = str.charAt(i);
                        nextState = new FiniteState();
                        lastState.addTransition(c, nextState);
                        previousState = lastState;
                        lastState = nextState;
                    } else {
                        return lastState;
                    }
                    break;
                default:
                    nextState = new FiniteState();
                    lastState.addTransition(c, nextState);
                    previousState = lastState;
                    lastState = nextState;
                    break;
            }  // switch
            lastCharacter = c;
        }

        return lastState;
    }

    /**
     * Parallel FiniteAutomaton builder for regex strings.
     * Parses the string character by charatcer, or clause by
     * clause if encountered, then connects each piece with
     * their respective transition rules in parallel. This
     * process may be tweaked by lambda transitions for "+"
     * and "*" loops, but the general idea remains the same.
     *
     * @param str The regex string to parse
     * @param begin The beginning state
     * @return The end state that all generated transitions are pointed to
     */
    private static FiniteState orBuild(String str, FiniteState begin) {
        FiniteState nextState, end;
        Stack<Character> charStack = new Stack<Character>();
        char lastCharacter = 0;
        char c;
        int i, j, length = str.length();
        end = new FiniteState();

        for (i = 0; i < length; i++) {
            c = str.charAt(i);

            switch (c) {
                case '(':
                    charStack.push('(');
                    j = i + 1;
                    nextState = Regex.build(str.substring(j), begin);
                    // After the call to this, there is a connected subgraph
                    // from lastState to nextState
                    i = Regex.moveCursor(str, j, charStack);
                    charStack.empty();
                    // Above i points to the index after the last possible ) or ]
                    if (i == -1) return null;
                    if (i < length) {
                        c = str.charAt(i);
                        if (c == '+') {
                            // This is not so optimized
                            nextState.addTransition("", begin);
                            end.addTransition("", nextState);
                            end = nextState;
                        } else if (c == '*') {
                            nextState.addTransition("", begin);
                            begin.addTransition("", end);
                        } else {  // Apply the default case
                            nextState.addTransition("", end);
                            begin.addTransition(c, end);
                        }
                    }

                    break;
                case ')':  // Topmost .build call won't return from this case
                    if (!charStack.isEmpty() && charStack.peek() == '(') charStack.pop();

                    if (charStack.isEmpty()) return end;
                    else return null;

                case '[':
                    charStack.push('[');
                    j = i + 1;
                    nextState = Regex.orBuild(str.substring(j), begin);
                    i = Regex.moveCursor(str, j, charStack);
                    charStack.empty();
                    // Above i points to the index after the last possible ) or ]
                    if (i == -1) return null;
                    if (i < length) {
                        c = str.charAt(i);
                        if (c == '+') {
                            // This is not so optimized
                            nextState.addTransition("", begin);
                            end.addTransition("", nextState);
                            end = nextState;
                        } else if (c == '*') {
                            nextState.addTransition("", begin);
                            begin.addTransition("", end);
                        } else {  // Apply the default case
                            nextState.addTransition("", end);
                            begin.addTransition(c, end);
                        }
                    }
                    break;
                case ']': // Topmost .build call won't return from this case

                    if (!charStack.isEmpty() && charStack.peek() == '[') charStack.pop();

                    if (charStack.isEmpty()) return end;
                    else return null;

                case '+':
                    // Below commented code is a different interpretation of "or"
                    // operation in regular languages. Apparently, the uncommented
                    // line is the correct interpretation.

                    //nextState = new FiniteState();
                    //begin.addTransition(lastCharacter, nextState);
                    //nextState.addTransition(lastCharacter, nextState);
                    //nextState.addTransition("", end);
                    end.addTransition(lastCharacter, end);
                    break;
                case '*':
                    begin.addTransition("", end);
                    begin.addTransition(lastCharacter, begin);
                    break;
                case '\\':
                    break;
                default:
                    begin.addTransition(c, end);
                    break;
            }  // switch
            lastCharacter = c;
        }

        return end;
    }

    private static int moveCursor(String str, int i, Stack<Character> charStack) {
        char c;
        while (!charStack.isEmpty()) {
            c = str.charAt(i);
            switch (c) {
                case '(':
                    charStack.push('(');
                    break;
                case ')':
                    if (charStack.peek() == '(') charStack.pop();
                    else return -1;  // Instead of null
                    break;
                case '[':
                    charStack.push('[');
                    break;
                case ']':
                    if (charStack.peek() == '[') charStack.pop();
                    else return -1;
                    break;
            }
            i++;
        }

        return i;  // The next character after the parentheses stack ends
    }

}
