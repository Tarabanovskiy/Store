package com.server.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения магазина.
 * Этот класс запускает Spring Boot приложение.
 */
@SpringBootApplication
public class StoreApplication {

	/**
	 * Точка входа в приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
}
