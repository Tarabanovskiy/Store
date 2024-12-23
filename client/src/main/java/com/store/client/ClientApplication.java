package com.store.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения клиента.
 * Этот класс запускает Spring Boot приложение.
 */
@SpringBootApplication
public class ClientApplication {

	/**
	 * Основной метод для запуска приложения.
	 *
	 * @param args Аргументы командной строки.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
