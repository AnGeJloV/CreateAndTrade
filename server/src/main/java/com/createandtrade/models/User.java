package com.createandtrade.models;

import com.createandtrade.models.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Сущность Пользователь.
 * Этот класс является "чертежом" для таблицы 'users' в базе данных.
 * Также он реализует интерфейс UserDetails для интеграции со Spring Security.
 */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email используется как логин
     */
    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private String phoneNumber;

    /**
     * Флаг, активен ли аккаунт. Используется для бана
     */
    @Column
    private boolean active = true;

    /**
     * Виртуальный баланс пользователя
     */
    @Column
    private double balance = 0.0;

    @Column(nullable = false, length = 1000)
    private String password;

    /**
     * Набор ролей пользователя.
     * EAGER загрузка означает, что роли загружаются сразу вместе с пользователем.
     * Хранится в отдельной связующей таблице 'user_role'.
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    /**
     * Список товаров, созданных этим пользователем.
     * LAZY загрузка означает, что товары загрузятся только при явном обращении.
     * mappedBy = "user" указывает, что за эту связь отвечает поле 'user' в классе Product.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();

    // =================================================================
    // Методы, требуемые интерфейсом UserDetails от Spring Security
    // =================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email; // В системе логином является email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Аккаунт никогда не истекает
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Аккаунт никогда не блокируется
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Пароль никогда не истекает
    }

    @Override
    public boolean isEnabled() {
        return true; // Аккаунт включен, если он активен
    }

}
