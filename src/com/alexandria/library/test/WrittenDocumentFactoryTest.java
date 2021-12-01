package com.alexandria.library.test;

import static org.junit.Assert.*;

import com.alexandria.library.domain.Book;
import com.alexandria.library.domain.Papyrus;
import com.alexandria.library.domain.WrittenDocumentFactory;
import org.junit.Test;

public class WrittenDocumentFactoryTest {

    @Test
    public void testSingleton() {
        assertEquals(WrittenDocumentFactory.getInstance(), WrittenDocumentFactory.getInstance());
    }

    @Test
    public void testGetBook() {
        Book book = WrittenDocumentFactory.getInstance().getBook();
        assertNotEquals(null, book);
    }

    @Test
    public void testGetPapyrus() {
        Papyrus papyrus = WrittenDocumentFactory.getInstance().getPapyrus();
        assertNotEquals(null, papyrus);
    }
}
