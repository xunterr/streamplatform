package com.xunterr.user.controller;

import com.xunterr.user.UserService;
import com.xunterr.user.model.User;
import com.xunterr.user.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserRequest request){
        userService.registerUser(request);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest request){
        userService.updateUser(id, request);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

}
