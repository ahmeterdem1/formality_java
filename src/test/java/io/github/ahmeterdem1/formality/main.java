package io.github.ahmeterdem1.formality;

import io.github.ahmeterdem1.formality.automata.FiniteAutomaton;
import io.github.ahmeterdem1.formality.state.FiniteState;
import io.github.ahmeterdem1.formality.automata.PushdownAutomaton;
import io.github.ahmeterdem1.formality.state.PushdownState;
import io.github.ahmeterdem1.formality.regex.Regex;

public class main {

    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c'};
        Alphabet alphabet = new Alphabet(chars);
        FiniteAutomaton machine = new FiniteAutomaton("test machine");
        machine.setAlphabet(alphabet);

        System.out.println(machine);

        FiniteState s1 = new FiniteState();
        FiniteState s2 = new FiniteState();
        FiniteState s3 = new FiniteState();

        machine.setBegin(s1);

        s1.addTransition("ab", s2);

        s2.addTransition("a", s1);
        s2.addTransition("ac", s3);

        s3.setFinal(true);

        String test = "abac";

        System.out.println(String.format("String: %s\nValidation: %b\n",
                                        test,
                                        machine.validate(test)));

        FiniteAutomaton regexmachine = Regex.compile("a(b)*");

        System.out.println(String.format("Regex Validation: %b\n\n",
                regexmachine.validate("ab")));

        // --------------------------------------------------------------------------

        PushdownAutomaton pmachine = new PushdownAutomaton("test pushdown");
        pmachine.setAlphabet(alphabet);
        pmachine.setStackAlphabet(alphabet);

        System.out.println(pmachine);

        PushdownState p1 = new PushdownState();
        PushdownState p2 = new PushdownState();

        p1.addTransition("a", "aa", p2, false);  // write
        p2.addTransition("b", "a", p2, true);  // read

        test = "aabbbb";  // should be false

        p2.setFinal(true);

        pmachine.setBegin(p1);

        System.out.println(String.format("String: %s\nValidation: %b\n",
                test,
                pmachine.validate(test)));




    }

}
