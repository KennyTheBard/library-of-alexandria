package com.alexandria.library.exceptions;

public class TextTooLongException extends Exception {
    public TextTooLongException(int length, int maxLength) {
        super("Text (" + length + ") longer than page (" + maxLength + ")");
    }
}
