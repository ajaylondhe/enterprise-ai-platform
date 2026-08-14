package com.enterpriseai.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeQueryService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public KnowledgeQueryService(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder) {

        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public KnowledgeQueryResult ask(String question) {

        System.out.println("========== RAG START ==========");
        System.out.println("Question: " + question);

        // STEP 1: Vector search
        System.out.println("STEP 1: Starting vector search...");

        List<Document> documents =
                vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(question)
                                .topK(2)
                                .build()
                );

        System.out.println(
                "STEP 1 COMPLETE: documents = "
                        + documents.size()
        );

        if (documents.isEmpty()) {

            return new KnowledgeQueryResult(
                    "The information is not available in the enterprise knowledge base.",
                    List.of()
            );
        }

        // STEP 2: Build context
        System.out.println("STEP 2: Building context...");

        String context =
                documents.stream()
                        .map(Document::getText)
                        .collect(
                                Collectors.joining("\n\n")
                        );

        if (context.length() > 6000) {
            context = context.substring(0, 6000);
        }

        System.out.println(
                "STEP 2 COMPLETE: context length = "
                        + context.length()
        );

        // STEP 3: Ollama
        System.out.println("STEP 3: Calling Ollama...");

        String answer =
                chatClient
                        .prompt()
                        .system("""
                                You are an Enterprise Knowledge Assistant.

                                Answer the user's question using ONLY the
                                enterprise knowledge provided below.

                                Rules:
                                - Do not use outside knowledge.
                                - Do not invent information.
                                - Do not assume missing information.
                                - If the answer is not present, say:
                                  "The information is not available in the
                                  enterprise knowledge base."
                                - Keep the answer concise and professional.

                                Enterprise knowledge:
                                %s
                                """.formatted(context))
                        .user(question)
                        .call()
                        .content();

        System.out.println(
                "STEP 3 COMPLETE: Ollama response received."
        );

        // STEP 4: Extract source names
        List<String> sources =
                documents.stream()
                        .map(document ->
                                (String) document
                                        .getMetadata()
                                        .get("source")
                        )
                        .filter(source ->
                                source != null &&
                                        !source.isBlank()
                        )
                        .distinct()
                        .toList();

        System.out.println(
                "Sources: " + sources
        );

        System.out.println("========== RAG COMPLETE ==========");

        return new KnowledgeQueryResult(
                answer,
                sources
        );
    }
}