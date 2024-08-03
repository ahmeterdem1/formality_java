package io.github.ahmeterdem1.formality;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * A wrapper class for a unique set of characters.
 */
public class Alphabet {

    private Set<Character> characters;

    /**
     * Initialize an empty Alphabet.
     */
    public Alphabet() {
        this.characters = new HashSet<Character>();
    }

    /**
     * Initialize an empty Alphabet and fill it with the given
     * characters.
     *
     * @param chars char array that holds the characters of the Alphabet
     */
    public Alphabet(char[] chars) {
        this.characters = new HashSet<Character>();
        for (char c : chars) {
            this.characters.add(c);
        }
    }

    /**
     * Initialize an empty Alphabet and fill it with the given
     * characters.
     *
     * @param chars Character array that holds the characters of the Alphabet
     */
    public Alphabet(Character[] chars) {
        this.characters = new HashSet<Character>();
        this.characters.addAll(Arrays.asList(chars));
    }

    /**
     * Assing the Alphabet to the given Set.
     *
     * @param s Set that holds the characters of the Alphabet
     */
    public Alphabet(Set<Character> s) {
        this.characters = s;
    }

    /**
     * Add characters to the Alphabet.
     *
     * @param chars char array of characters
     */
    public void setCharacters(char[] chars) {
        for (char c : chars) {
            this.characters.add(c);
        }
    }

    /**
     * Add characters to the Alphabet.
     *
     * @param chars Character array of characters
     */
    public void setCharacters(Character[] chars) {
        this.characters.addAll(Arrays.asList(chars));
    }

    /**
     * Add characters to the Alphabet.
     *
     * @param str String containing all characters
     */
    public void setCharacters(String str) {
        for (char c: str.toCharArray()) {
            this.characters.add(c);
        }
    }

    public final Set<Character> getCharacters() {
        return this.characters;
    }

    /**
     * Get the character count of the Alphabet
     *
     * @return Count of unique characters
     */
    public final int getLength() {
        return this.characters.toArray().length;
    }

    /**
     * Get the character count of the Alphabet
     *
     * @return Count of unique characters
     */
    public final int getSize() {
        return this.characters.toArray().length;
    }

    /**
     * Check if the Alphabet contains the given character.
     *
     * @param c char primitive to check for
     * @return true if contains, false otherwise
     */
    public boolean contains(char c) {
        return this.characters.contains(c);
    }

    /**
     * Check if the Alphabet contains the given character.
     *
     * @param c Character object to check for
     * @return true if contains, false otherwise
     */
    public boolean contains(Character c) {
        return this.characters.contains(c);
    }

    public String toString() {
        return this.characters.toString();
    }

    public Object[] toArray() {
        return this.characters.toArray();
    }

    /**
     * Check if the Alphabet contains the given character.
     *
     * @param c char primitive to check for
     * @return true if contains, false otherwise
     */
    public boolean validate(char c) {
        char[] array = {c};
        char ch;
        String str = new String(array);

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (!this.characters.contains(ch)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if each character of the given char array
     * is in the Alphabet.
     *
     * @param c char array to check for
     * @return true if contains, false otherwise
     */
    public boolean validate(char[] c) {
        char ch;
        String str = new String(c);

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (!this.characters.contains(ch)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the Alphabet contains the given character.
     *
     * @param c Character object to check for
     * @return true if contains, false otherwise
     */
    public boolean validate(Character c) {
        char ch;
        String str = Character.toString(c);

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (!this.characters.contains(ch)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if each character of the given String
     * is in the Alphabet.
     *
     * @param str String to check for
     * @return true if contains, false otherwise
     */
    public boolean validate(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!this.characters.contains(c)) {
                return false;
            }
        }

        return true;
    }

}
