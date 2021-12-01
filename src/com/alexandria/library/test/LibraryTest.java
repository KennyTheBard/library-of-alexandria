package com.alexandria.library.test;

import com.alexandria.library.domain.*;
import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;
import com.alexandria.library.exceptions.ProcessorClosedException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LibraryTest {

    @Test
    public void testAddDocuments() throws InterruptedException, EmptyDocumentException, MissingTitleException, ProcessorClosedException {
        Library library = new Library(1);
        library.processDocument(new Papyrus("Test1", "12 34"));
        library.closeLibrary();
        LibraryReport report = library.extractReport();

        assertEquals(1, report.getDocumentCount());
        assertEquals(2, report.getWordCount());
        assertEquals(5, report.getCharCount());
    }

    @Test
    public void testAddTwoDocuments() throws InterruptedException, EmptyDocumentException, MissingTitleException, ProcessorClosedException {
        Library library = new Library(1);
        library.processDocument(new Papyrus("Test1", "12 34"));
        library.processDocument(new Papyrus("Test2", "56 78"));
        library.closeLibrary();
        LibraryReport report = library.extractReport();

        assertEquals(2, report.getDocumentCount());
        assertEquals(4, report.getWordCount());
        assertEquals(10, report.getCharCount());
    }

    @Test(expected = ProcessorClosedException.class)
    public void testCloseLibrary() throws InterruptedException, EmptyDocumentException, MissingTitleException, ProcessorClosedException {
        Library library = new Library(1);
        library.closeLibrary();
        library.processDocument(new Papyrus("Test1", "12 34"));
    }
}
