package com.createandtrade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурационный класс для настроек безопасности.
 */
@Configuration
public class SecurityConfig {

    /**
     * Создает и настраивает бин PasswordEncoder.
     * Мы используем BCrypt - надежный и стандартный алгоритм хэширования.
     * @return реализацию PasswordEncoder, которую Spring будет использовать во всем приложении.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
