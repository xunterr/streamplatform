package com.xunterr.user.controller;

import com.xunterr.user.service.UserService;
import com.xunterr.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDTO get(@PathVariable UUID id){
        return userService.getById(id);
    }

    @PostMapping("/query")
    public UserDTO getByQuery(UserDTO userDTO){
        return userService.search(userDTO);
    }


    @PutMapping(path = "{id}")
    public void update(@PathVariable UUID id, @RequestBody UserDTO request){
        userService.updateUser(id, request);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable UUID id){
        userService.deleteById(id);
    }

}
