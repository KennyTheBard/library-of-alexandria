package com.alexandria.library.test;

import com.alexandria.library.domain.Book;
import com.alexandria.library.domain.Market;
import com.alexandria.library.domain.WrittenDocument;
import com.alexandria.library.domain.WrittenDocumentProcessor;
import com.alexandria.library.exceptions.EmptyDocumentException;
import com.alexandria.library.exceptions.MissingTitleException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarketTest {

    @Test
    public void testMarket() throws InterruptedException {
        int merchantsNum = 10;
        List<WrittenDocument> syncList = Collections.synchronizedList(new ArrayList<>());
        Market market = new Market(syncList::add, merchantsNum);
        Thread thread = new Thread(market);
        thread.start();
        thread.join();

        assertEquals(merchantsNum, syncList.size());
    }

}
