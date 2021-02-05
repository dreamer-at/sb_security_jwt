package com.mysbdemos.security_v1_demo.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String  firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Builder.Default
    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Column(name = "role")
    private Role role;
}
