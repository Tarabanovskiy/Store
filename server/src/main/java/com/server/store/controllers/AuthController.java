package com.server.store.controllers;

import com.server.store.JWT.JwtUtil;
import com.server.store.models.Role;
import com.server.store.models.User;
import com.server.store.services.UserDetailsServiceImpl;
import com.server.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Контроллер для управления аутентификацией пользователей.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Регистрирует нового пользователя.
     *
     * @param user пользователь для регистрации
     * @return ответ с сообщением о результате регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Set<Role> validRoles = Set.of(Role.ADMIN, Role.MANAGER, Role.CUSTOMER);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body("Роли не указаны или пусты.");
        }

        for (Role role : user.getRoles()) {
            if (!validRoles.contains(role)) {
                return ResponseEntity.badRequest().body("Недопустимая роль: " + role + ".");
            }
        }

        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
    }

    /**
     * Аутентифицирует пользователя и возвращает JWT токен.
     *
     * @param user пользователь для аутентификации
     * @return ответ с JWT токеном или сообщением об ошибке
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Неверный логин или пароль.");
        }
    }
}

