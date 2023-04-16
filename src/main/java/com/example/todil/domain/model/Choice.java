package com.example.todil.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Data
@Getter
@Setter
public class Choice implements Serializable {

    private Integer index;

    private String text;

    @JsonProperty("finish_reason")
    private String finishReason;

}

