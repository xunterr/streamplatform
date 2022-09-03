package com.xunterr.user;

import com.xunterr.user.exception.AlreadyTakenException;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import com.xunterr.user.model.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return user.orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
    }


    public void registerUser(UserRequest request) {
        if(repository.findByUsername(request.username()).isPresent()){
            throw new AlreadyTakenException("Username already taken");
        }
        if(repository.findByEmail(request.email()).isPresent()){
            throw new AlreadyTakenException("Email already taken");
        }
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
        repository.save(newUser);
    }

    @Transactional
    public void updateUser(Long id, UserRequest request){
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
