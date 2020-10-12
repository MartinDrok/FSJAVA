package com.mdrok._spring_rest.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    NONBINARY("nonbinary"),
    UNCOMMITED("uncommited");

    private String name;

    Gender(String name){ this.name = name; }

    @JsonValue
    public String getName(){ return name; }
}
