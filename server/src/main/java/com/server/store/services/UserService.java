package com.server.store.services;

import com.server.store.exceptions.ResourceNotFoundException;
import com.server.store.models.User;
import com.server.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления пользователями.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Сохраняет нового пользователя.
     *
     * @param user пользователь для сохранения
     * @return сохраненный пользователь
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    /**
     * Проверяет существование пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return true, если пользователь существует, иначе false
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}