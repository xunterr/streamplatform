package com.xunterr.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class User {
        @Id
        @SequenceGenerator(
                name = "user_id_sequence",
                sequenceName = "user_id_sequence")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
        @Column(name = "id", nullable = false)
        private Long id;

        private String username;
        private String email;
        private String password;
}
