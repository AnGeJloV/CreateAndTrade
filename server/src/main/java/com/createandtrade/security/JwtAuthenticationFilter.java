package com.createandtrade.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр, который перехватывает каждый запрос для проверки JWT-токена.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Пытаемся извлечь токен из запроса
        String token = resolveToken(request);

        // 2. Проверяем, что токен существует и он валиден
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // 3. Если токен валиден, получаем из него объект Authentication
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // 4. Помещаем объект Authentication в SecurityContext.
            // Spring Security будет знать, что текущий пользователь аутентифицирован
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 5. Передаем запрос дальше по цепочке фильтров
        filterChain.doFilter(request, response);
    }

    /**
     * Вспомогательный метод для извлечения токена из заголовка Authorization.
     */
    private String resolveToken (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
