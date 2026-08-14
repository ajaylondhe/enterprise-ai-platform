package com.enterpriseai.ai.controller;

import com.enterpriseai.ai.dto.AiKnowledgeRequest;
import com.enterpriseai.ai.dto.AiRequest;
import com.enterpriseai.ai.dto.AiResponse;
import com.enterpriseai.ai.service.AiService;

import com.enterpriseai.ai.service.KnowledgeQueryResult;
import com.enterpriseai.ai.service.KnowledgeQueryService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final KnowledgeQueryService knowledgeQueryService;

    public AiController(AiService aiService, KnowledgeQueryService knowledgeQueryService) {
        this.aiService = aiService;
        this.knowledgeQueryService = knowledgeQueryService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AiResponse> ask(
            @Valid @RequestBody AiRequest request) {

        return ResponseEntity.ok(
                aiService.ask(request)
        );
    }
    @PostMapping("/knowledge/ask")
    public ResponseEntity<AiResponse> askKnowledge(
            @Valid @RequestBody AiKnowledgeRequest request) {

        KnowledgeQueryResult result =
                knowledgeQueryService.ask(
                        request.getQuestion()
                );

        return ResponseEntity.ok(
                new AiResponse(
                        request.getQuestion(),
                        result.getAnswer(),
                        result.getSources()
                )
        );
    }
}