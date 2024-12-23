package com.server.store.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Утилита для работы с JWT токенами.
 */
@Component
public class JwtUtil {

    private final String SECRET_KEY = "c4F1kM8MwL97dcbSkz82uBzT6TtDkPq3ZbGfv9qCnR3a9AsvBbKj8U5Zg6m48WhP";

    /**
     * Извлекает имя пользователя из JWT токена.
     *
     * @param token JWT токен
     * @return имя пользователя
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлекает дату истечения срока действия из JWT токена.
     *
     * @param token JWT токен
     * @return дата истечения срока действия
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает утверждение из JWT токена.
     *
     * @param token JWT токен
     * @param claimsResolver функция для извлечения утверждения
     * @param <T> тип утверждения
     * @return утверждение
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Извлекает все утверждения из JWT токена.
     *
     * @param token JWT токен
     * @return все утверждения
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Проверяет, истек ли срок действия JWT токена.
     *
     * @param token JWT токен
     * @return true, если срок действия истек, иначе false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Генерирует JWT токен для пользователя.
     *
     * @param userDetails информация о пользователе
     * @return сгенерированный JWT токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Создает JWT токен с указанными утверждениями и субъектом.
     *
     * @param claims утверждения
     * @param subject субъект
     * @return сгенерированный JWT токен
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Проверяет валидность JWT токена для пользователя.
     *
     * @param token JWT токен
     * @param userDetails информация о пользователе
     * @return true, если токен валиден, иначе false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

