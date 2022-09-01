package com.xunterr.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService{
    UserRepository repository;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public void registerUser(UserRegistrationRequest request) {
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
        repository.save(newUser);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
