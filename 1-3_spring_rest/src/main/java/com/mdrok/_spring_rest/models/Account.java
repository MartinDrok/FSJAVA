package com.mdrok._spring_rest.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.UUID;

public @Data class Account {
    private UUID id;
    @NotNull(message = "Cannot be null.")
    @Size( min = 15, max = 34, message = "the required amount of characters for an IBAN is between {min} and {max}.")
    private String IBAN;
    @NotNull(message = "Cannot be null.")
    private Float balance;
    @NotNull(message = "Cannot be null.")
    private LinkedList<Holder> holders;

    public Account(String IBAN, Float balance, LinkedList<Holder> holders) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.holders = holders;
    }
}
