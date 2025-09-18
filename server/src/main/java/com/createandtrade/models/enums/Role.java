package com.createandtrade.models.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление для ролей пользователей в системе.
 * Реализует GrantedAuthority, чтобы быть совместимым со Spring Security.
 */

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

/**
 * Spring Security использует этот метод для получения строкового представления роли.
 */
    @Override
    public String getAuthority() {
        return name();
    }
}
