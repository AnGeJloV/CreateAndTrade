package com.createandtrade.controllers;

import com.createandtrade.dto.RegistrationRequest;
import com.createandtrade.dto.UserDto;
import com.createandtrade.models.User;
import com.createandtrade.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    /**
     * Эндпоинт для регистрации нового пользователя.
     * Принимает POST-запросы на /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegistrationRequest registrationRequest){
        User newUser = userService.createUser(registrationRequest);
        UserDto userDto = modelMapper.map(newUser, UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
