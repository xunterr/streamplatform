package com.xunterr.user.service;

import com.xunterr.user.dto.UserDTO;
import com.xunterr.user.repository.UserRepository;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
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

    public void updateUser(UUID id, UserDTO request){
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

	public UserDTO search(UserDTO request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
        User result = repository.findOne(Example.of(user))
                .orElseThrow(() -> new EntityNotFoundException(request.getId(), "User not found"));
        return new UserDTO(result);
	}
}
