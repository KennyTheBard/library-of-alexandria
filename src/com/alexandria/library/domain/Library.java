package com.alexandria.library.domain;

import com.alexandria.library.exceptions.ProcessorClosedException;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Library implements WrittenDocumentProcessor {

    private final Map<String, WrittenDocument> collection = new HashMap<>();
    private final Queue<WrittenDocument> queue = new LinkedBlockingQueue<>();
    private final Map<Clerk, Thread> clerks = new HashMap<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);

    public Library(int clerksNum) {
        for (int i = 0; i < clerksNum; i++) {
            Clerk clerk = new Clerk(this);
            clerks.put(clerk, new Thread(clerk));
        }
        clerks.values().forEach(Thread::start);
    }

    public void processDocument(WrittenDocument doc) throws ProcessorClosedException {
        if (!isOpen.get()) {
            throw new ProcessorClosedException("Library received document after closing");
        }

        queue.add(doc);
    }

    public void closeLibrary() throws InterruptedException {
        isOpen.set(false);
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.wait();
            }
        }
        clerks.keySet().forEach(Clerk::cancel);
        for (Thread clerkThread : clerks.values()) {
            clerkThread.join();
        }
    }

    public LibraryReport extractReport() {
        return new LibraryReport(
                this.getDocumentsCount(),
                this.getWordCount(),
                this.getCharCount()
        );
    }

    private void addNewDocument(WrittenDocument doc) {
        boolean shouldClone = true;
        // make sure that documents will not have duplicates
        synchronized (collection) {
            shouldClone = !collection.containsKey(doc.getTitle());
            if (shouldClone) {
                collection.put(doc.getTitle(), null);
            }
        }

        if (shouldClone) {
            collection.put(doc.getTitle(), doc.clone());
            System.out.println("> Currently transcribing '" + doc.getTitle() + "'...");
        }
        System.out.println("Merchant with '" + doc.getTitle() + "' can leave");
    }

    private int getDocumentsCount() {
        return collection.size();
    }

    private int getWordCount() {
        return collection.values()
                .stream()
                .map(WrittenDocument::read)
                .map(s -> Arrays.stream(s.split("[.!?, ]"))
                        .filter(e -> !e.isEmpty())
                        .map(e -> 1)
                        .reduce(0, Integer::sum))
                .reduce(0, Integer::sum);
    }

    private int getCharCount() {
        return collection.values()
                .stream()
                .map(WrittenDocument::read)
                .map(String::length)
                .reduce(0, Integer::sum);
    }

    private static class Clerk implements Runnable {

        private final Library lib;
        private volatile boolean cancelled = false;

        public Clerk(Library lib) {
            this.lib = lib;
        }

        public void cancel() {
            cancelled = true;
        }

        @Override
        public void run() {
            try {
                while (!cancelled) {
                    WrittenDocument doc = lib.queue.poll();
                    if (doc == null) {
                        synchronized (lib.queue) {
                            lib.queue.notify();
                        }
                        Thread.sleep(100);
                    } else {
                        lib.addNewDocument(doc);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
