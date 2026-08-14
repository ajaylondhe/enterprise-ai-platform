package com.enterpriseai.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class AiKnowledgeRequest {

    @NotBlank
    private String question;

    public AiKnowledgeRequest() {
    }

    public AiKnowledgeRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}