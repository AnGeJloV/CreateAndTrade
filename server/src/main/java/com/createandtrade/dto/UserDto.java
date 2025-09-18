package com.createandtrade.dto;

import com.createandtrade.models.enums.Role;
import lombok.Data;

import java.util.Set;

/**
 * DTO для безопасной передачи данных о пользователе клиенту.
 * Не содержит пароль и другие чувствительные данные.
 */
@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private boolean active;
    private double balance;
    private Set<Role> roles;
}
