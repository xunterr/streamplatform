package com.xunterr.user.dto;


import com.xunterr.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper{
    public UserDTO toDto(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public User toUser(UserDTO dto){
        return User.builder()
                .username(dto.username)
                .email(dto.email)
                .build();
    }
}
