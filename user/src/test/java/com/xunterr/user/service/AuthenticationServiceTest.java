package com.xunterr.user.service;

import com.xunterr.user.dto.AuthenticationRequest;
import com.xunterr.user.model.Role;
import com.xunterr.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserDetailsService userDetailsService;

    AuthenticationService underTest;

    @BeforeEach
    void beforeEach(){
        this.underTest = new AuthenticationService(jwtService,
                authenticationManager, userDetailsService);
    }

    @Test
    void whenAuthenticate_shouldAuthenticateUser() {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .roles(List.of(Role.USER))
                .build();
        AuthenticationRequest request = new AuthenticationRequest(
                user.getUsername(),
                user.getPassword()
        );
        given(userDetailsService.loadUserByUsername(user.getUsername()))
                .willReturn(user);

        //when
        underTest.authenticate(request);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> argumentCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        //then
        verify(authenticationManager).authenticate(argumentCaptor.capture());
    }

    @Test
    @SuppressWarnings("unchecked")
    void whenAuthenticate_shouldGenerateToken() {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .roles(List.of(Role.USER))
                .build();
        AuthenticationRequest request = new AuthenticationRequest(
                user.getUsername(),
                user.getPassword()
        );
        given(userDetailsService.loadUserByUsername(user.getUsername()))
                .willReturn(user);

        //when
        underTest.authenticate(request);
        ArgumentCaptor<Map<String, Object>> argumentCaptor =
                ArgumentCaptor.forClass(Map.class);

        //then
        verify(jwtService).generateToken(argumentCaptor.capture(), eq(user));
    }
}