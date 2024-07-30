package io.github.ahmeterdem1.formality.state;

import java.util.HashMap;
import java.util.Map;

/**
 * A state class structured for pushdown automata. Implements
 * a respective stack structure that is manipulated with transition
 * rules.
 */
public class PushdownState extends State {

    private Map<String, String> readStack;
    private Map<String, String> writeStack;

    public PushdownState() {
        super();
        this.readStack = new HashMap<String, String>();
        this.writeStack = new HashMap<String, String>();
    }

    public PushdownState(boolean flag) {
        super(flag);
        this.readStack = new HashMap<String, String>();
        this.writeStack = new HashMap<String, String>();
    }

    /**
     * Get the dictated read operations on the stack.
     *
     * @return The transition rule-stack rule couples for read operations
     */
    public Map<String, String> getReadStack() {
        return this.readStack;
    }

    /**
     * Get the dictated write operations on the stack.
     *
     * @return The transition rule-stack rule couples for write operations
     */
    public Map<String, String> getWriteStack() {
        return this.writeStack;
    }

    /**
     * Add a transition rule for a pushdown automata. It is also
     * needed to provide stack manipulation rule with the transition
     * rule. Both char's for the language and the stack will be interpreted
     * as String objects.
     *
     * @param c A char from the language alphabet
     * @param d A char from the stack alphabet
     * @param s Target state to transition to
     * @param access Option to read or write on stack; true for read, false for write
     */
    public void addTransition(char c, char d, PushdownState s, boolean access) {
        char[] array_l = {c};  // language
        char[] array_s = {d};  // stack
        this.transitions.put(new String(array_l), s);
        if (access) {
            this.readStack.put(new String(array_l), new String(array_s));
        } else {
            this.writeStack.put(new String(array_l), new String(array_s));
        }
    }

    /**
     * Add a transition rule for a pushdown automata. It is also
     * needed to provide stack manipulation rule with the transition
     * rule. Both char arrays for the language and the stack will be
     * interpreted as String objects.
     *
     * @param c A char array from the language alphabet
     * @param d A char array from the stack alphabet
     * @param s Target state to transition to
     * @param access Option to read or write on stack; true for read, false for write
     */
    public void addTransition(char[] c, char[] d, PushdownState s, boolean access) {
        this.transitions.put(new String(c), s);

        if (access) {
            this.readStack.put(new String(c), new String(d));
        } else {
            this.writeStack.put(new String(c), new String(d));
        }
    }

    /**
     * Add a transition rule for a pushdown automata. It is also
     * needed to provide stack manipulation rule with the transition
     * rule. Both Character's for the language and the stack will be
     * interpreted as String objects.
     *
     * @param c A Character from the language alphabet
     * @param d A Character from the stack alphabet
     * @param s Target state to transition to
     * @param access Option to read or write on stack; true for read, false for write
     */
    public void addTransition(Character c, Character d, PushdownState s, boolean access) {
        this.transitions.put(Character.toString(c), s);

        if (access) {
            this.readStack.put(Character.toString(c), Character.toString(d));
        } else {
            this.writeStack.put(Character.toString(c), Character.toString(d));
        }
    }

    /**
     * Add a transition rule for a pushdown automata. It is also
     * needed to provide stack manipulation rule with the transition
     * rule.
     *
     * @param c A String from the language alphabet
     * @param d A String from the stack alphabet
     * @param s Target state to transition to
     * @param access Option to read or write on stack; true for read, false for write
     */
    public void addTransition(String c, String d, PushdownState s, boolean access) {
        this.transitions.put(c, s);

        if (access) {
            this.readStack.put(c, d);
        } else {
            this.writeStack.put(c, d);
        }
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a char primitive. The char will be interpreted as a String.
     *
     * @param c char primitive as the transition rule
     */
    public void removeTransition(char c) {
        char[] array = {c};
        String str = new String(array);
        this.transitions.remove(str);
        this.readStack.remove(str);
        this.writeStack.remove(str);
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a char array. The char will be interpreted as a String.
     *
     * @param c char array as the transition rule
     */
    public void removeTransition(char[] c) {
        String str = new String(c);
        this.transitions.remove(str);
        this.readStack.remove(str);
        this.writeStack.remove(str);
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a Character. The char will be interpreted as a String.
     *
     * @param c Character as the transition rule
     */
    public void removeTransition(Character c) {
        String str = Character.toString(c);
        this.transitions.remove(str);
        this.readStack.remove(str);
        this.writeStack.remove(str);
    }

    /**
     * Remove a transition rule from the state, where the match rule is
     * a String.
     *
     * @param str String as the transition rule
     */
    public void removeTransition(String str) {
        this.transitions.remove(str);
        this.readStack.remove(str);
        this.writeStack.remove(str);
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a char primitive
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(char c) {
        char[] array = {c};
        String match = this.getMatch(new String(array));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }
        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a char array
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(char[] c) {
        String match = this.getMatch(new String(c));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param c Transition rule to match, as a Character object
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(Character c) {
        String match = this.getMatch(Character.toString(c));
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Perform maximum matching and returns the corresponding State.
     *
     * @param str Transition rule to match, as a String
     * @return Returns the State if the rule exists, null otherwise
     */
    public State get(String str) {
        String match = this.getMatch(str);
        if (this.transitions.containsKey(match)) {
            return this.transitions.get(match);
        }

        return null;
    }

    /**
     * Get the matching string operation on stack for the given transition
     * rule, regardless of the read or write option. Returns null if the
     * transition does not exist.
     *
     * @param match The string to match to transition rules
     * @return Matching String operation on the stack, can be null
     */
    public String getStackMatch(String match) {
        if (this.readStack.containsKey(match)) {
            return this.readStack.get(match);
        }

        if (this.writeStack.containsKey(match)) {
            return this.writeStack.get(match);
        }

        return null;
    }

    /**
     * Get the matching string operation on stack for the given transition
     * rule, for the read option. Returns empty String if the rule does not
     * exist.
     *
     * @param match The string to match to transition rules
     * @return Matching String read operation on the stack
     */
    public String getReadMatch(String match) {
        if (this.readStack.containsKey(match)) {
            return this.readStack.get(match);
        }

        return "";
    }

    /**
     * Get the matching string operation on stack for the given transition
     * rule, for the write option. Returns empty String if the rule does not
     * exist.
     *
     * @param match The string to match to transition rules
     * @return Matching String read operation on the stack
     */
    public String getWriteMatch(String match) {
        if (this.writeStack.containsKey(match)) {
            return this.writeStack.get(match);
        }

        return "";
    }


}
