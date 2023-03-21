package com.xunterr.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
        @NotNull
        UUID id;

        @NotBlank(message = "username cannot be blank")
        String username;
        @NotBlank(message = "email cannot be blank")
        String email;
}
