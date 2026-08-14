package com.enterpriseai.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AiRequest {

    @NotBlank(message = "Question is required")
    @Size(
            min = 3,
            max = 2000,
            message = "Question must be between 3 and 2000 characters"
    )
    private String question;

    public AiRequest() {
    }

    public AiRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}