package com.server.store.services;

import com.server.store.config.UserDetailsImpl;
import com.server.store.models.User;
import com.server.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для загрузки информации о пользователе по имени пользователя.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Загружает информацию о пользователе по имени пользователя.
     *
     * @param username имя пользователя
     * @return информация о пользователе
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином не найден: " + username));
        return new UserDetailsImpl(user);
    }
}
