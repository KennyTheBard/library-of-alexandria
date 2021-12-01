package com.alexandria.library.test;

import com.alexandria.library.domain.Book;
import com.alexandria.library.domain.Papyrus;
import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;
import com.alexandria.library.exceptions.TextTooLongException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WrittenDocumentTest {

    @Test
    public void testBook() throws EmptyDocumentException, MissingTitleException {
        String name = "Test Book";
        String content = "Test Content";
        Book book = new Book(name, content);
        assertEquals(name, book.getTitle());
        assertEquals(content, book.read());
    }

    @Test(expected = MissingTitleException.class)
    public void testBookMissingTitleException() throws EmptyDocumentException, MissingTitleException {
        new Book("", "");
    }

    @Test(expected = EmptyDocumentException.class)
    public void testBookEmptyDocumentException() throws EmptyDocumentException, MissingTitleException {
        new Book("Test", "");
    }

    @Test(expected = TextTooLongException.class)
    public void testBookPageTextTooLongException() throws EmptyDocumentException, TextTooLongException {
        new Book.Page(new String(new char[101]).replace('\0', 'A'));
    }

    @Test
    public void testBookWithPages() throws EmptyDocumentException, TextTooLongException {
        String name = "Test Book";
        List<String> content = List.of("Test1", "Test2", "Test3");
        List<Book.Page> pages = new ArrayList<>();
        for (String pageContent : content) {
            pages.add(new Book.Page(pageContent));
        }
        Book book = new Book(name, pages);

        assertEquals(String.join(" ", content), book.read());
    }

    @Test
    public void testBookClone() throws EmptyDocumentException, MissingTitleException {
        String name = "Test Book";
        String content = "Test Content";
        Book book = new Book(name, content);
        Book clonedBook = (Book) book.clone();

        assertEquals(name, clonedBook.getTitle());
        assertEquals(content, clonedBook.read());
    }

    @Test
    public void testBookPage() throws EmptyDocumentException, TextTooLongException {
        Book.Page page = new Book.Page("Test");
        assertEquals(String.valueOf(page.hashCode()), page.getTitle());
    }

    @Test
    public void testPapyrus() throws EmptyDocumentException, MissingTitleException {
        String name = "Test Book";
        String content = "Test Content";
        Book book = new Book(name, content);
        assertEquals(name, book.getTitle());
        assertEquals(content, book.read());
    }

    @Test(expected = MissingTitleException.class)
    public void testPapyrusMissingTitleException() throws EmptyDocumentException, MissingTitleException {
        new Papyrus("", "");
    }

    @Test(expected = EmptyDocumentException.class)
    public void testPapyrusEmptyDocumentException() throws EmptyDocumentException, MissingTitleException {
        new Papyrus("Test", "");
    }

    @Test
    public void testPapyrusClone() throws EmptyDocumentException, MissingTitleException {
        String name = "Test Book";
        String content = "Test Content";
        Papyrus papyrus = new Papyrus(name, content);
        Papyrus clonedPapyrus = (Papyrus) papyrus.clone();

        assertEquals(name, clonedPapyrus.getTitle());
        assertEquals(content, clonedPapyrus.read());
    }
}
