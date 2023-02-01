package com.xunterr.user;

import com.xunterr.user.exception.AlreadyTakenException;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import com.xunterr.user.model.UserDTO;
import com.xunterr.user.model.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService{
    UserRepository repository;
    UserDTOMapper userDTOMapper;

    public List<UserDTO> getAll(){
        return repository.findAll()
                .stream()
                .map(userDTOMapper)
                .toList();
    }

    public UserDTO getById(UUID id) {
        return repository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
    }


    public void registerUser(UserDTO request) {
        if(repository.findByUsername(request.getUsername()).isPresent()){
            throw new AlreadyTakenException("Username already taken");
        }
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new AlreadyTakenException("Email already taken");
        }
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(null)
                .build();
        repository.save(newUser);
    }

    public void updateUser(UUID id, UserDTO request){
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
        user.setUsername(request.getUsername());
        user.setPassword(null);
        user.setEmail(request.getEmail());
    }

    public void deleteById(UUID id){
        Optional<User> user = repository.findById(id);
        repository.delete(user.orElseThrow(() -> new EntityNotFoundException(id, "User not found")));
    }
}
