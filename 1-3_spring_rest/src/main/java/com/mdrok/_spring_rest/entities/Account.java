package com.mdrok._spring_rest.entities;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity(name="account")
@Table(name="account")
public @Data class Account extends BaseEntity {
    @NotNull(message = "Cannot be null.")
    private Boolean active;

    @Size( min = 15, max = 34, message = "the required amount of characters for an IBAN is between {min} and {max}.")
    @NotNull(message = "Cannot be null.")
    private String iban;

    @NotNull(message = "Cannot be null.")
    private Float balance;

    @NotNull(message = "Cannot be null.")
    @OneToMany(mappedBy="account")
    private List<Holder> holders;
}
