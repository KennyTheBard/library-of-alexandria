package com.alexandria.library.domain;

public class LibraryReport {

    private final int documentCount;
    private final int wordCount;
    private final int charCount;

    public LibraryReport(int documentCount, int wordCount, int charCount) {
        this.documentCount = documentCount;
        this.wordCount = wordCount;
        this.charCount = charCount;
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getCharCount() {
        return charCount;
    }
}
