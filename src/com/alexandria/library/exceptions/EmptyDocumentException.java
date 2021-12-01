package com.alexandria.library.exceptions;

public class EmptyDocumentException extends Exception {
    public EmptyDocumentException() {
        super("Documents cannot have empty texts");
    }
}
