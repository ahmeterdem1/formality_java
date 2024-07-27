package io.github.ahmeterdem1.formality;

import io.github.ahmeterdem1.formality.automata.FiniteAutomaton;
import io.github.ahmeterdem1.formality.state.FiniteState;

public class main {

    public static void main(String[] args) {
        char[] chars = {'a', 'b'};
        Alphabet alphabet = new Alphabet(chars);
        FiniteAutomaton machine = new FiniteAutomaton("test machine");
        machine.setAlphabet(alphabet);

        System.out.println(machine);

        FiniteState s1 = new FiniteState();
        FiniteState s2 = new FiniteState();
        FiniteState s3 = new FiniteState();

        machine.setBegin(s1);

        s1.addTransition("a", s2);
        s1.addTransition("b", s3);

        s2.addTransition("a", s1);
        s2.addTransition("b", s1);

        s3.addTransition("b", s3);

        String test = "abba";

        System.out.println(String.format("String: %s\nValidation: %b\n",
                                        test,
                                        machine.validate(test)));
    }

}
