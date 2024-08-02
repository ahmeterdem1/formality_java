package io.github.ahmeterdem1.formality.regex;

import io.github.ahmeterdem1.formality.regex.Match;
import io.github.ahmeterdem1.formality.Alphabet;
import io.github.ahmeterdem1.formality.state.FiniteState;
import io.github.ahmeterdem1.formality.automata.FiniteAutomaton;

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
                    // Above i points to the index after the last possible ) or ]
                    if (i == -1) return null;
                    if (i < length) {
                        c = str.charAt(i);
                        if (c == '+') {
                            nextState.addTransition("", lastState);
                        } else if (c == '*') {
                            nextState.addTransition("", lastState);
                            lastState.addTransition("", nextState);
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
                    nextState = Regex.build(str.substring(j), begin);
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
