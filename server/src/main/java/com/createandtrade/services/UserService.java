package com.createandtrade.services;

import com.createandtrade.dto.RegistrationRequest;
import com.createandtrade.models.Cart;
import com.createandtrade.models.User;
import com.createandtrade.models.enums.Role;
import com.createandtrade.repositories.CartRepository;
import com.createandtrade.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для управления бизнес-логикой, связанной с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser (RegistrationRequest request){
        // 1. Проверяем, не занят ли email
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        // 2. Создаем новый объект User и заполняем его данными
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(Role.ROLE_USER);
        user.setRoles(defaultRoles);

        User savedUser = userRepository.save(user);

        createCartForUser(savedUser);

        return savedUser;
    }

    /**
     * Вспомогательный метод для создания корзины для нового пользователя.
     */
    private void createCartForUser (User user){
        if (cartRepository.findByUser(user).isEmpty()){
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
    }
}
