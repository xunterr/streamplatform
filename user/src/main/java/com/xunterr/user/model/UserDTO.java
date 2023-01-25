package com.xunterr.user.model;

import javax.validation.constraints.NotBlank;

public record UserDTO(

        @NotBlank(message = "username cannot be blank")
        String username,

        @NotBlank(message = "email cannot be blank")
        String email,

        @NotBlank
        String streamKey
) {
}
