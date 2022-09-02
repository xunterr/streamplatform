package com.xunterr.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRequest(

        @NotBlank(message = "username cannot be blank")
        String username,

        @NotBlank(message = "email cannot be blank")
        String email,

        @NotBlank(message = "password cannot be blank")
        String password
) {
}
