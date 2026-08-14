package com.enterpriseai.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        You are an Enterprise AI Assistant.

                        Your role is to help employees and managers
                        with HR and workplace-related questions.

                        Give clear, professional and concise answers.
                        Do not invent company policies or employee information.
                        If required information is missing, clearly say so.
                        """)
                .build();
    }
}