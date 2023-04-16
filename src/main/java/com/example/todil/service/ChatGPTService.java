package com.example.todil.service;

import com.example.todil.domain.model.ChatGPTResponse;

public interface ChatGPTService {

    ChatGPTResponse askQuestion(String msg);

}
