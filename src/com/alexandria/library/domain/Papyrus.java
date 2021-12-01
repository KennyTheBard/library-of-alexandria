package com.alexandria.library.domain;

import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;


public class Papyrus extends WrittenDocument {

    private final String title;
    private final String text;

    public Papyrus(String title, String text) throws EmptyDocumentException, MissingTitleException {
        if (title == null || title.isEmpty()) {
            throw new MissingTitleException();
        }
        this.title = title;
        if (text == null || text.isEmpty()) {
            throw new EmptyDocumentException();
        }
        this.text = text;
    }

    @Override
    public String read() {
        return text;
    }

    @Override
    public WrittenDocument clone() {
        try {
            Thread.sleep((long) MILLIS_PER_CHAR * text.length());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            return new Papyrus(this.title, this.text);
        } catch (EmptyDocumentException | MissingTitleException e) {
            e.printStackTrace();
            return null; // this should never happen as the clone of a valid document is a valid document as well
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
