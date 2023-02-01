package com.xunterr.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
        @JsonIgnore
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
