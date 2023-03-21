package com.xunterr.user.service;

import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.User;
import com.xunterr.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;
    UserService underTest;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach(){
        this.underTest = new UserService(repository, passwordEncoder);
    }

    @Test
    void canGetAll() {
        underTest.getAll();
        verify(repository).findAll();
    }

    @Test
    void canGetById() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Username",
                "Email", "password", null);

        given(repository.findById(id)).willReturn(Optional.of(user));
        User result = underTest.getById(id);

        verify(repository).findById(id);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenUpdateUser_shouldUpdateUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Username",
                "Email", "password", null);

        given(repository.findById(id)).willReturn(Optional.of(user));
        underTest.updateById(id, user);

        verify(repository).save(user);
    }

    @Test
    void whenUpdateUser_userDoesNotExist_shouldThrowNotFound() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Username",
                "Email", "password", null);
        assertThatThrownBy(() -> underTest.updateById(id, user))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void whenDeleteById_shouldDeleteUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Username",
                "Email", "password", null);

        given(repository.findById(id)).willReturn(Optional.of(user));
        underTest.deleteById(id);
        verify(repository).delete(user);
    }

    @Test
    void whenDeleteById_userDoesNotExist_shouldThrowNotFound() {
        UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> underTest.deleteById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void whenSearchByUsername_shouldFindUser() {
        User user = User.builder()
                .username("Username")
                .build();

        given(repository.findOne(Example.of(user))).willReturn(Optional.of(user));
        underTest.search(user);
        verify(repository).findOne(Example.of(user));
    }
}