package com.createandtrade.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для настроек безопасности.
 */
@Configuration
@EnableWebSecurity
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

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /**
     * Бин, который управляет процессом аутентификации.
     * Spring Security будет использовать его для проверки логина и пароля.
     */
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF (Cross-Site Request Forgery) защиту.
                // Она не нужна для REST API, где аутентификация происходит по токенам.
                .csrf(AbstractHttpConfigurer::disable)

                // Настройка правил авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
