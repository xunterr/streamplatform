package com.xunterr.user.controller;

import com.xunterr.user.dto.UserDTOMapper;
import com.xunterr.user.model.User;
import com.xunterr.user.service.UserService;
import com.xunterr.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    UserService userService;
    UserDTOMapper mapper;

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal")
    public UserDTO get(@PathVariable String id){
        User result = userService.getById(UUID.fromString(id));
        return mapper.toDto(result);
    }

    @PostMapping("/query")
    public UserDTO getByQuery(UserDTO userDTO){
        User query = mapper.toUser(userDTO);
        return mapper.toDto(userService.search(query));
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("#id == authentication.principal")
    public void update(@PathVariable String id, @RequestBody UserDTO request){
        User user = mapper.toUser(request);
        userService.updateById(UUID.fromString(id), user);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("#id == authentication.principal")
    public void delete(@PathVariable String id){
        userService.deleteById(UUID.fromString(id));
    }
}