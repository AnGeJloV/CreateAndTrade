package com.createandtrade.controllers;

import com.createandtrade.dto.AuthRequest;
import com.createandtrade.dto.AuthResponse;
import com.createandtrade.dto.RegistrationRequest;
import com.createandtrade.dto.UserDto;
import com.createandtrade.models.User;
import com.createandtrade.services.UserService;
import com.createandtrade.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

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

    /**
     * Эндпоинт для входа пользователя в систему.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody AuthRequest authRequest){
        // 1. Запускаем стандартный процесс аутентификации Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        // 2. Если аутентификация прошла успешно, пользователь находится в 'authentication.getPrincipal()'
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        UserDto userDto = modelMapper.map (user, UserDto.class);

        // 3. Генерируем JWT-токен
        String token = jwtTokenProvider.createToken(authentication);

        //4. Возвращаем токен и данные пользователя
        return ResponseEntity.ok(new AuthResponse(token, userDto));
    }
}
