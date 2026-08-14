package com.enterpriseai.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeIngestionService {

    private final VectorStore vectorStore;

    public KnowledgeIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public int ingestKnowledgeBase() throws IOException {

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources =
                resolver.getResources(
                        "classpath:/knowledge-base/**/*.md"
                );

        List<Document> documents = new ArrayList<>();

        for (Resource resource : resources) {

            String content =
                    resource.getContentAsString(java.nio.charset.StandardCharsets.UTF_8);

            Document document =
                    new Document(content);

            document.getMetadata()
                    .put("source", resource.getFilename());

            documents.add(document);
        }

        if (documents.isEmpty()) {
            return 0;
        }

        TokenTextSplitter splitter =
                TokenTextSplitter.builder()
                        .withChunkSize(500)
                        .withMinChunkSizeChars(200)
                        .withMinChunkLengthToEmbed(20)
                        .withMaxNumChunks(100)
                        .build();

        List<Document> chunks =
                splitter.apply(documents);

        vectorStore.add(chunks);

        return chunks.size();
    }
}