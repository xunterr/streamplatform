package com.xunterr.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
        @Id
        @GeneratedValue
        private UUID id;

        private String username;
        private String email;
        private String password;
        private boolean isLive;

        @Enumerated(EnumType.STRING)
        private List<Role> roles;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .toList();
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
