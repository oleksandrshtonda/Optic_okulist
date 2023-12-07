package spring.boot.optic.okulist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.optic.okulist.dto.user.UserLoginRequestDto;
import spring.boot.optic.okulist.dto.user.UserLoginResponseDto;
import spring.boot.optic.okulist.dto.user.UserPasswordUpdateRequestDto;
import spring.boot.optic.okulist.dto.user.UserRegistrationRequestDto;
import spring.boot.optic.okulist.dto.user.UserResponseDto;
import spring.boot.optic.okulist.dto.user.UserUpdateRequestDto;
import spring.boot.optic.okulist.exception.EntityNotFoundException;
import spring.boot.optic.okulist.exception.RegistrationException;
import spring.boot.optic.okulist.security.AuthenticationService;
import spring.boot.optic.okulist.service.user.UserService;
import spring.boot.optic.okulist.service.user.passwordreset.UserPasswordUpdateService;

@Tag(name = "Authentication management", description = "Endpoints for managing authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        logger.info("Received registration request for user with email: {}", requestDto.getEmail());
        return userService.register(requestDto);
    }

    @Operation(summary = "Login", description = "Login")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
