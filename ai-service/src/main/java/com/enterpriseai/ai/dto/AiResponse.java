package com.enterpriseai.ai.dto;

import java.util.List;

public class AiResponse {

    private String question;

    private String answer;

    private List<String> sources;

    public AiResponse() {
    }

    public AiResponse(
            String question,
            String answer) {

        this.question = question;
        this.answer = answer;
        this.sources = List.of();
    }

    public AiResponse(
            String question,
            String answer,
            List<String> sources) {

        this.question = question;
        this.answer = answer;
        this.sources = sources;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
}