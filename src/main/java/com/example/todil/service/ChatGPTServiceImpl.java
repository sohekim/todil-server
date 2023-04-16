package com.example.todil.service;

import com.example.todil.domain.model.ChatGPTRequest;
import com.example.todil.domain.model.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService{

    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${app.api-key}")
    private String apiKey;

    public HttpEntity<ChatGPTRequest> buildHttpEntity(ChatGPTRequest chatRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        // todo: hide bearer token
        headers.add("Authorization", "Bearer "+apiKey);
        return new HttpEntity<>(chatRequest, headers);
    }

    public ChatGPTResponse getResponse(HttpEntity<ChatGPTRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGPTResponse> responseEntity = restTemplate.postForEntity(
                "https://api.openai.com/v1/completions",
                chatRequestHttpEntity,
                ChatGPTResponse.class);

        return responseEntity.getBody();
    }

    public ChatGPTResponse askQuestion(String msg) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGPTRequest(
                                "text-davinci-003",
                                msg,
                                0.0,
                                300,
                                1.0)));
    }


}
