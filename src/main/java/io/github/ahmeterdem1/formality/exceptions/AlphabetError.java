package io.github.ahmeterdem1.formality.exceptions;

public class AlphabetError extends Exception {

    public AlphabetError() {
        super();
    }

    public AlphabetError(String hint) {
        super(hint);
    }
}
