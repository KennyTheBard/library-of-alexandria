package com.alexandria.library.exceptions;

public class MissingTitleException extends Exception {
    public MissingTitleException() {
        super("Every document must have a title");
    }
}
