package com.enterpriseai.ai.controller;

import com.enterpriseai.ai.service.VectorStoreTestService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/vector")
public class VectorStoreTestController {

    private final VectorStoreTestService vectorStoreTestService;

    public VectorStoreTestController(
            VectorStoreTestService vectorStoreTestService) {

        this.vectorStoreTestService =
                vectorStoreTestService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {

        vectorStoreTestService.addTestDocument();

        return ResponseEntity.ok(
                "Document successfully added to vector store"
        );
    }
}