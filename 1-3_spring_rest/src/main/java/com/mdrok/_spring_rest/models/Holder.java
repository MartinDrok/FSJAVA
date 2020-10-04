package com.mdrok._spring_rest.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public @Data class Holder {
    private UUID id;
    @Size( min = 1, message = "the minimum required amount of characters for a first name is {min}.")
    @NotNull(message = "Cannot be null.")
    private String firstName;
    @Size( min = 1, message = "the minimum required amount of characters for a last name is {min}.")
    @NotNull(message = "Cannot be null.")
    private String lastName;

    public Holder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
