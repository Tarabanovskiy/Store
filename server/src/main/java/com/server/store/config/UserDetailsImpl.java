package com.server.store.config;

import com.server.store.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link UserDetails} для пользователя.
 */
public class UserDetailsImpl implements UserDetails {

    private final User user;

    /**
     * Конструктор, принимающий объект пользователя.
     *
     * @param user пользователь
     */
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * Возвращает объект пользователя.
     *
     * @return объект пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Возвращает набор прав доступа пользователя.
     *
     * @return набор прав доступа пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи.
     *
     * @return всегда true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирована ли учетная запись.
     *
     * @return всегда true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истек ли срок действия учетных данных.
     *
     * @return всегда true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, активна ли учетная запись.
     *
     * @return всегда true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

