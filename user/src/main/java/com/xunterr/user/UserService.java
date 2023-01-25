package com.xunterr.user;

import com.xunterr.user.exception.AlreadyTakenException;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import com.xunterr.user.model.UserDTO;
import com.xunterr.user.model.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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

    public UserDTO getById(Long id) {
        return repository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
    }



    public void registerUser(UserDTO request) {
        if(repository.findByUsername(request.username()).isPresent()){
            throw new AlreadyTakenException("Username already taken");
        }
        if(repository.findByEmail(request.email()).isPresent()){
            throw new AlreadyTakenException("Email already taken");
        }
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(null)
                .streamKey(request.streamKey())
                .build();
        repository.save(newUser);
    }

    @Transactional
    public void updateUser(Long id, UserDTO request){
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User not found"));
        user.setUsername(request.username());
        user.setPassword(null);
        user.setEmail(request.email());
        user.setStreamKey(request.streamKey());
    }

    public void deleteById(Long id){
        Optional<User> user = repository.findById(id);
        repository.delete(user.orElseThrow(() -> new EntityNotFoundException(id, "User not found")));
    }
}
