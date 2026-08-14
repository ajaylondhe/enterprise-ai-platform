package com.enterpriseai.ai.service;

import java.util.List;

public class KnowledgeQueryResult {

    private final String answer;

    private final List<String> sources;

    public KnowledgeQueryResult(
            String answer,
            List<String> sources) {

        this.answer = answer;
        this.sources = sources;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getSources() {
        return sources;
    }
}