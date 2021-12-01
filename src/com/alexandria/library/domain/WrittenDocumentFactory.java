package com.alexandria.library.domain;

import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class WrittenDocumentFactory {

    private static WrittenDocumentFactory instance = null;

    public static WrittenDocumentFactory getInstance() {
        if (instance == null) {
            instance = new WrittenDocumentFactory();
        }
        return instance;
    }

    private final String fillerText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laborisnisi ut aliquip ex ea commodo consequat. " +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private int count = 1;

    private WrittenDocumentFactory() {}

    public Papyrus getPapyrus() {
        try {
            return new Papyrus(this.getTitle(), getFillerText(ThreadLocalRandom.current().nextInt(1, 6)));
        } catch (EmptyDocumentException | MissingTitleException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBook() {
        try {
            return new Book(this.getTitle(), getFillerText(ThreadLocalRandom.current().nextInt(1, 11)));
        } catch (EmptyDocumentException | MissingTitleException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFillerText(int n) {
        return String.join(" ", Collections.nCopies(n, fillerText));
    }

    /**
     * Returns a new title from documents each time, excepting 1/5 of cases, when it returns an already existing title
     * @return title for documents
     */
    private String getTitle() {
        return (count > 0 && ThreadLocalRandom.current().nextInt() % 3 == 0)
                ? "Document " + ThreadLocalRandom.current().nextInt(1, count + 1)
                : "Document " + (count++);
    }

}
