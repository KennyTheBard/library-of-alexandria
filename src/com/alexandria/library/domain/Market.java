package com.alexandria.library.domain;

import com.alexandria.library.exceptions.ProcessorClosedException;

import java.util.concurrent.ThreadLocalRandom;

public class Market implements Runnable {

    private final WrittenDocumentProcessor processor;
    private final int merchantsNum;

    public Market(WrittenDocumentProcessor processor, int merchantsNum) {
        this.processor = processor;
        this.merchantsNum = merchantsNum;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < merchantsNum; i++) {
                WrittenDocument doc = ThreadLocalRandom.current().nextInt() % 2 == 0
                        ? WrittenDocumentFactory.getInstance().getBook()
                        : WrittenDocumentFactory.getInstance().getPapyrus();
                System.out.println("New merchant in the dockyard with '" + doc.getTitle() + "' on them");
                processor.processDocument(doc);
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
            }
        } catch (InterruptedException | ProcessorClosedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
