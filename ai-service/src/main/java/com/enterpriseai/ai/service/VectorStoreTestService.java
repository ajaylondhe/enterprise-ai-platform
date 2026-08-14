package com.enterpriseai.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorStoreTestService {

    private final VectorStore vectorStore;

    public VectorStoreTestService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void addTestDocument() {

        Document document =
                new Document(
                        "Employees should submit sick leave requests through the approved employee leave system."
                );

        vectorStore.add(List.of(document));
    }
}