package com.server.store.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * Сущность, представляющая пользователя.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальное имя пользователя, не может быть null.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Полное имя пользователя, не может быть null.
     */
    @Column(nullable = false)
    private String fullName;

    /**
     * Пароль пользователя, не может быть null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роли пользователя. Хранятся в отдельной таблице "user_roles".
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;
}