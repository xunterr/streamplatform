package com.xunterr.user.controller;

import com.xunterr.user.UserService;
import com.xunterr.user.model.User;
import com.xunterr.user.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public UserDTO get(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("/{id}/stream-key")
    public String getStreamKey(@PathVariable Long id){
        return userService.getById(id).streamKey();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDTO request){
        userService.registerUser(request);
    }

    @PutMapping(path = "{id}")
    public void update(@PathVariable Long id, @RequestBody UserDTO request){
        userService.updateUser(id, request);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        userService.deleteById(id);
    }

}
