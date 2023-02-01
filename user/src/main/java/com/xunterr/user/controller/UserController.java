package com.xunterr.user.controller;

import com.xunterr.user.UserService;
import com.xunterr.user.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaUrl;

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable UUID id){
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDTO request){
        userService.registerUser(request);
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
