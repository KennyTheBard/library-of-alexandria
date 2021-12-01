package com.alexandria.library.domain;

import com.alexandria.library.exceptions.ProcessorClosedException;

public interface WrittenDocumentProcessor {
    void processDocument(WrittenDocument document) throws ProcessorClosedException;
}
