package com.xunterr.user.controller;

import com.xunterr.user.service.UserService;
import com.xunterr.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal")
    public UserDTO get(@PathVariable String id){
        return userService.getById(UUID.fromString(id));
    }

    @PostMapping("/query")
    public UserDTO getByQuery(UserDTO userDTO){
        return userService.search(userDTO);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("#id == authentication.principal")
    public void update(@PathVariable String id, @RequestBody UserDTO request){
        userService.updateUser(UUID.fromString(id), request);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("#id == authentication.principal")
    public void delete(@PathVariable String id){
        userService.deleteById(UUID.fromString(id));
    }
}