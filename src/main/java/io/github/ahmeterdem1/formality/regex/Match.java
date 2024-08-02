package io.github.ahmeterdem1.formality.regex;

/**
 * A generic class to hold string matches,
 * found by regular expression operations.
 */
public class Match {

    private String match;
    private int begin;
    private int end;

    public Match() {
        this.begin = -1;
        this.end = -1;
        this.match = "";
    }

    public Match(String str, int begin) {
        this.begin = begin;
        this.match = str;
        this.end = this.begin + str.length();  // Of course
    }

    /**
     * Get the beginning index of the match, respect to the
     * beginning of the original string that the search is
     * done.
     *
     * @return The integer marking the index of the matched String's beginning,
     *      respect to the original String
     */
    public final int getBegin() {
        return this.begin;
    }

    /**
     * Get the end index of the match, respect to the
     * beginning of the original string that the search is
     * done.
     *
     * @return The integer marking the index of the matched String's last character,
     *      respect to the original String
     */
    public final int getEnd() {
        return this.end;
    }

    /**
     * Get the matched string by the regex engine. The returned
     * string must belong to the language.
     *
     * @return The String that is matched by the regex engine
     */
    public final String getMatch() {
        return this.match;
    }

    @Override
    public final String toString() {
        return String.format("Match(match='%s', begin=%d, end=%d)",
                             this.match, this.begin, this.end);
    }
}
