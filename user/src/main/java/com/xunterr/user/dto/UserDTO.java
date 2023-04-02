package com.xunterr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
        private UUID id;
        private String username;
        private String email;
        private boolean isLive;
}
