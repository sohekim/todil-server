package com.example.todil.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;

@Data
@Getter
@Setter
public class ChatGPTResponse implements Serializable {

    private String id;

    private String object;

    private String model;

    private LocalDate created;

    private List<Choice> choices;
}