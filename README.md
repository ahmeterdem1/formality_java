# Formality

This is the Java version of the Formality that has been initiated in C++. Probably, 
all developments will continue in Java.

View the javadocs for proper documentation.

[![javadoc](https://javadoc.io/badge2/io.github.ahmeterdem1/formality/javadoc.svg)](https://javadoc.io/doc/io.github.ahmeterdem1/formality)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[See Package](https://central.sonatype.com/artifact/io.github.ahmeterdem1/formality/)

## Project Details

This project aims to create text parsing, regex and language processing tools from the 
ground up. All underlying algorithms will be implemented in Java. 

The said tools cover basic state machine simulators for Type-3 and Type-2 languages.
Implementations of those state machines really are "simulators" as they are formed
as a graph with transition rules, where the machine passes from state to state as
it tries to match read text substrings to the transition rules that it has at hand.

In the near future, this library will have its own regex engine, solely based on
FiniteAutomata class. The goal is to build a "compiler" that forms the corresponding
graph for the state machine representing the regex string.

Another plan related to regex, is another compiler that builds the corresponding DFA
from any given NFA/DFA. 

After the regex engine and related tools are finished, this library will continue its 
life as a custom NLP library featuring tokenizers and text processing tools based on 
itself. 

Even though there are many approaches to the text parsing *problem*, this library
is intended to be a solution which is internally simple and transparent, enhancing
the approachability of all the algorithms used in the CS/NLP field, that are more or
less complex. 

I also have other libraries that are written in pure Python, and tries to accomplish
the same approachability problem for numerical analysis and machine learning. 
[Vectorgebra](https://github.com/ahmeterdem1/Vector)
and [MLgebra](https://github.com/ahmeterdem1/ml) 
are the best examples, where I implement *all* algorithms at *all*
layers of abstraction, to make the complex backends of algorithms surface.

There is also [semantic space](https://github.com/ahmeterdem1/semantic-space) 
library, which is again in pure Python, that is 
intended to be merged with this project. Semantic space includes tokenization and
NLP modeling tools, where except the numerical backend which uses Numpy, is built
from the ground up at all possible levels of abstraction.

## Regex Engine

The current regex compiler in the library, is not yet complete. For now, it only features
"(", ")", "[", "]", "+" and "*" special symbols, other than the language alphabet. 

The engine works recursively. Each subclause in a regex string, must also be a regex.
The main idea is to create "sub-finite-state-machines", which are then from top to the bottom of the
recursion stack, plugged together to yield a single final finite state machine.

Pluggings that require loops ("+" and "*"), are done via lambda transitions. Lambda transitions
are transitions whose rule are empty. Empty transitions are checked by the validator, as the
last possible case. If no match is found, the related method returns an empty string, which
by definition is a lambda transition.

 
````java

import io.github.ahmeterdem1.formality.Alphabet;
import io.github.ahmeterdem1.formality.automata.FiniteAutomaton;
import io.github.ahmeterdem1.formality.state.FiniteState;
import io.github.ahmeterdem1.formality.automata.PushdownAutomaton;
import io.github.ahmeterdem1.formality.state.PushdownState;
import io.github.ahmeterdem1.formality.regex.Regex;
import io.github.ahmeterdem1.formality.regex.Match;

import java.util.ArrayList;

public class main {
    
    public static void main (String args[]) {
        char[] chars = {'a', 'b', 'c'};
        
        // Every state machine has an alphabet that it
        // is defined on.
        Alphabet alphabet = new Alphabet(chars);
        
        // You can name your machine
        FiniteAutomaton machine = new FiniteAutomaton("test machine");
        machine.setAlphabet(alphabet);  // Set the alphabet of the machine

        System.out.println(machine);  // Try it!

        // A state machine, is the entrance to a graph
        // formed by State objects.
        FiniteState s1 = new FiniteState();
        FiniteState s2 = new FiniteState();
        FiniteState s3 = new FiniteState();

        // Set the entrance of the graph, the initial state.
        machine.setBegin(s1);
        // Any Automaton object will ONLY hold the
        // beginning State in it.

        // Add a transition rule that is s1->s2 
        // if "ab" string is read while the machine
        // is in the s1 state.
        s1.addTransition("ab", s2);

        s2.addTransition("a", s1);
        s2.addTransition("ac", s3);
        
        // Mark the state as a "final" state
        s3.setFinal(true);
        
        // The regex for the language represented:
        //           ab(aab)*ac

        String test = "abac";

        System.out.println(String.format("String: %s\nValidation: %b\n",
                test,
                machine.validate(test)));
        // "validate" method returns a boolean, marking
        // if the given string is accepted by the language.
        // For the string to be accepted, the machine should be 
        // in a State which is marked "final", and there should
        // be no String left to read.

        // Compile the regex string into a FiniteAutomata
        FiniteAutomaton regexmachine = Regex.compile("a(b)*");

        System.out.println(String.format("Regex Validation: %b\n\n",
                regexmachine.validate("ab")));
        // Use validate method to utilize regex

        String text = "abbab";
        // Find all matches in a text with regex
        ArrayList<Match> matches = Regex.match(text, "a(b)+");
        // 3 possible matches in total.
        System.out.println(matches);
    }
    
}

````


