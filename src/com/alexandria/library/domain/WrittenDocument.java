package com.alexandria.library.domain;


public abstract class WrittenDocument implements Cloneable {

    public static final int MILLIS_PER_CHAR = 1;

    public abstract String read();

    public abstract WrittenDocument clone();

    public String getTitle() {
        return String.valueOf(hashCode());
    }

}
