package com.mdrok._spring_rest.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdrok._spring_rest.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(value="account")
@Entity(name="holder")
@Table(name="holder")
public @Data class Holder extends BaseEntity {
    @Size( min = 1, message = "the minimum required amount of characters for a first name is {min}.")
    @NotNull(message = "Cannot be null.")
    private String firstName;

    @Size( min = 1, message = "the minimum required amount of characters for a last name is {min}.")
    @NotNull(message = "Cannot be null.")
    private String lastName;

    @Embedded
    @NotNull(message = "Cannot be null.")
    private Address address;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Enumerated
    @NotNull(message = "Cannot be null.")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
}
