package com.enterpriseai.ai.service;

import com.enterpriseai.ai.dto.AiRequest;
import com.enterpriseai.ai.dto.AiResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder) {

        this.chatClient =
                chatClientBuilder.build();
    }

    public AiResponse ask(AiRequest request) {

        String answer =
                chatClient
                        .prompt()
                        .system("""
                            You are an Enterprise AI Assistant.

                            Provide accurate and professional answers.
                            Use clear and simple language.

                            Do not invent company policies, employee information,
                            financial information, or confidential information.

                            If the required information is unavailable,
                            clearly say that you do not have enough information.

                            Keep every answer concise.
                            Prefer answers under 80 words.
                            """)
                        .user(request.getQuestion())
                        .call()
                        .content();

        return new AiResponse(
                request.getQuestion(),
                answer
        );
    }
}