package com.alexandria.library.domain;

import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;
import com.alexandria.library.exceptions.TextTooLongException;

import java.util.ArrayList;
import java.util.List;

public class Book extends WrittenDocument {

    private final String title;
    private final List<Page> pages;

    public Book(String title, String text) throws EmptyDocumentException, MissingTitleException {
        if (title == null || title.isEmpty()) {
            throw new MissingTitleException();
        }
        this.title = title;
        this.pages = new ArrayList<>();
        if (text.isEmpty()) {
            throw new EmptyDocumentException();
        }

        int startIndex = 0;
        do {
            try {
                int endIndex = Math.min(startIndex + Page.MAX_CHAR_PER_PAGE, text.length());
                Page page = new Page(text.substring(startIndex, endIndex));
                this.pages.add(page);
            } catch (TextTooLongException e) {
                e.printStackTrace();
            }

            startIndex += Page.MAX_CHAR_PER_PAGE;
        } while (startIndex < text.length());
    }

    public Book(String title, List<Page> pages) {
        this.title = title;
        this.pages = pages;
    }

    @Override
    public String read() {
        return String.join(" ", pages.stream().map(Page::read).toList());
    }

    @Override
    public WrittenDocument clone() {
        List<Page> copies = new ArrayList<>();
        for (Page p : this.pages) {
            copies.add((Page) p.clone());
        }
        return new Book(this.title, copies);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static class Page extends WrittenDocument {

        public static final int MAX_CHAR_PER_PAGE = 100;

        private final String text;

        public Page(String text) throws TextTooLongException, EmptyDocumentException {
            if (text == null || text.isEmpty()) {
                throw new EmptyDocumentException();
            }
            if (text.length() > MAX_CHAR_PER_PAGE) {
                throw new TextTooLongException(text.length(), MAX_CHAR_PER_PAGE);
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
                return new Page(this.text);
            } catch (TextTooLongException | EmptyDocumentException e) {
                e.printStackTrace();
                return null; // this should never happen as the clone of a valid document is a valid document as well
            }
        }
    }

}
