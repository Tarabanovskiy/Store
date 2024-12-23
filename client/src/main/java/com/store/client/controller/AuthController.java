package com.store.client.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Контроллер для управления аутентификацией и регистрацией пользователей.
 * Обрабатывает запросы на вход, регистрацию и отображение страниц.
 */
@Controller
public class AuthController {

    private final String BACKEND_URL = "http://localhost:8080/auth";

    /**
     * Отображает страницу входа в систему.
     *
     * @return Имя представления для страницы входа.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Обрабатывает запрос на вход в систему.
     * Отправляет запрос на бэкенд с данными пользователя для проверки и авторизации.
     * Сохраняет токен в сессии, если вход успешен.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param model Модель для передачи данных на страницу.
     * @param session Сессия для хранения токена авторизации.
     * @return Имя представления для перенаправления или страницы ошибки.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Создаем JSON-запрос
            String requestJson = """
                {
                    "username": "%s",
                    "password": "%s"
                }
            """.formatted(username, password);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            // Отправляем POST-запрос на бэкенд
            ResponseEntity<String> response = restTemplate.exchange(
                    BACKEND_URL + "/login",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                // Сохраняем токен в сессии
                String token = response.getBody();
                session.setAttribute("jwtToken", token);

                return "redirect:/dashboard/main_dashboard";
            } else {
                model.addAttribute("error", "Неправильный логин или пароль.");
                return "auth/login";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                model.addAttribute("error", "Неправильный логин или пароль.");
            } else {
                model.addAttribute("error", "Ошибка входа: " + e.getMessage());
            }
            return "auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка входа: " + e.getMessage());
            return "auth/login";
        }
    }

    /**
     * Отображает страницу регистрации нового пользователя.
     *
     * @return Имя представления для страницы регистрации.
     */
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    /**
     * Обрабатывает запрос на регистрацию нового пользователя.
     * Отправляет данные пользователя на бэкенд для регистрации.
     *
     * @param username Имя пользователя.
     * @param fullName Полное имя пользователя.
     * @param password Пароль пользователя.
     * @param role Роль пользователя.
     * @param model Модель для передачи данных на страницу.
     * @return Имя представления для перенаправления или страницы ошибки.
     */
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String fullName,
                           @RequestParam String password, @RequestParam String role,
                           Model model) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Создаем JSON-запрос
            String requestJson = """
                {
                    "username": "%s",
                    "fullName": "%s",
                    "password": "%s",
                    "roles": ["%s"]
                }
            """.formatted(username, fullName, password, role);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            // Отправляем POST-запрос на бэкенд
            restTemplate.exchange(
                    BACKEND_URL + "/register",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка регистрации: " + e.getMessage());
            return "auth/register";
        }
    }

    /**
     * Отображает страницу "Об авторе".
     *
     * @return Имя представления для страницы "Об авторе".
     */
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}
