package com.enterpriseai.ai.controller;

import com.enterpriseai.ai.service.KnowledgeIngestionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai/knowledge")
public class KnowledgeController {

    private final KnowledgeIngestionService ingestionService;

    public KnowledgeController(
            KnowledgeIngestionService ingestionService) {

        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest()
            throws IOException {

        int count =
                ingestionService.ingestKnowledgeBase();

        return ResponseEntity.ok(
                "Knowledge ingestion completed. Chunks stored: "
                        + count
        );
    }
}