package com.mdrok._spring_rest.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public @Data class Address {
    @NotNull(message = "Cannot be null.")
    private String street;

    @NotNull(message = "Cannot be null.")
    private String zipCode;

    @NotNull(message = "Cannot be null.")
    private String city;
}
