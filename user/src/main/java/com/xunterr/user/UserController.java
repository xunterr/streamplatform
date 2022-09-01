package com.xunterr.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public record UserController (UserService userService) {


    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        return userService.getById(id);
    }

    @PostMapping
    public void registerUser(@RequestBody UserRegistrationRequest request){
        userService.registerUser(request);
    }
}
