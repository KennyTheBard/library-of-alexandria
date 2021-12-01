package com.alexandria.library.main;

import com.alexandria.library.domain.Market;
import com.alexandria.library.domain.Library;

import java.util.concurrent.ThreadLocalRandom;

public class LibraryApplication {

    public static void main(String[] args) {
        Library library = new Library(3);
        Market market = new Market(library, ThreadLocalRandom.current().nextInt(5, 20));
        Thread marketThread = new Thread(market);
        marketThread.start();

        try {
            marketThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            library.closeLibrary();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(library.extractReport());
        System.out.println("The library is closed!");
    }

}
