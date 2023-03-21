package com.xunterr.user.service;

import com.xunterr.user.dto.RegisterRequest;
import com.xunterr.user.exception.AlreadyTakenException;
import com.xunterr.user.model.Role;
import com.xunterr.user.repository.UserRepository;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService{
    UserRepository repository;
    PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
    }

    public User create(RegisterRequest request){
        if(repository.findByUsername(request.username()).isPresent()){
            throw new AlreadyTakenException("Username already taken");
        }
        if(repository.findByEmail(request.email()).isPresent()){
            throw new AlreadyTakenException("Email already taken");
        }
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        return repository.saveAndFlush(user);
    }

    public void updateById(UUID id, User request){
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
        user.setUsername(request.getUsername());
        user.setPassword(null);
        user.setEmail(request.getEmail());
        repository.save(user);
    }

    public void deleteById(UUID id){
        Optional<User> user = repository.findById(id);
        repository.delete(user.orElseThrow(() -> new EntityNotFoundException(id, "User not found")));
    }

	public User search(User request) {
        return repository.findOne(Example.of(request))
                .orElseThrow(() -> new EntityNotFoundException(request.getId(), "User not found"));
	}
}
