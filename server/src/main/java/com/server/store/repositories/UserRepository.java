package com.server.store.repositories;

import com.server.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Репозиторий для управления пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return Optional, содержащий пользователя, если найден, иначе пустой Optional
     */
    Optional<User> findByUsername(String username);



    /**
     * Проверяет существование пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);
}
