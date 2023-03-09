package com.xunterr.user.dto;

import com.xunterr.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
        @NotNull
        UUID id;

        @NotBlank(message = "username cannot be blank")
        String username;
        @NotBlank(message = "email cannot be blank")
        String email;

        public UserDTO(User user){
                this(user.getId(), user.getUsername(), user.getEmail());
        }
}
