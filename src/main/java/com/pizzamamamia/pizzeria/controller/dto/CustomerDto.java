package com.pizzamamamia.pizzeria.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizzamamamia.pizzeria.controller.validation.Phone;
import com.pizzamamamia.pizzeria.controller.validation.group.OnCreate;
import com.pizzamamamia.pizzeria.controller.validation.group.OnUpdate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;

    @Email
    @Null(message = "'email' should be absent in request",groups = OnUpdate.class)
    @NotBlank(message = "'email' shouldn't be empty",groups = OnCreate.class)
    private String email;

    @NotBlank(message = "'first name' shouldn't be empty")
    private String firstName;

    @NotBlank(message = "'last name' shouldn't be empty")
    private String lastName;

    @Null(message = "'phone' should be absent in request",groups = OnUpdate.class)
    @Phone(groups = OnCreate.class)
    private String phone;

    @NotBlank(message = "'address line' shouldn't be empty")
    private String addressLine;
}
