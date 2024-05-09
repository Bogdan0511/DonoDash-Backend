package com.backend.DonoDash.service.impl;

import com.backend.DonoDash.dto.AuthenticationDTO;
import com.backend.DonoDash.dto.RegistrationDTO;
import com.backend.DonoDash.dto.UserDTO;
import com.backend.DonoDash.enums.TokenType;
import com.backend.DonoDash.enums.UserType;
import com.backend.DonoDash.model.Token;
import com.backend.DonoDash.model.User;
import com.backend.DonoDash.persistence.TokenRepository;
import com.backend.DonoDash.persistence.UserRepository;
import com.backend.DonoDash.security.JwtService;
import com.backend.DonoDash.service.interfaces.UserService;
import com.backend.DonoDash.utils.exception.AlreadyExistsException;
import com.backend.DonoDash.utils.exception.BadInputException;
import com.backend.DonoDash.utils.exception.NotFoundException;
import com.backend.DonoDash.utils.mapper.UserMapper;
import com.backend.DonoDash.utils.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDTO login(AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getEmail(), authenticationDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(authenticationDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("There is no user registered with this email!"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return userMapper.entityToDTO(user, jwtToken, refreshToken);
    }

    @Override
    public UserDTO register(RegistrationDTO registrationDTO) {

        if(userValidator.validateName(registrationDTO.getFirstName())) {
            throw new BadInputException("Invalid first name!");
        }
        if(userValidator.validateName(registrationDTO.getLastName())) {
            throw new BadInputException("Invalid last name!");
        }
        if(!userValidator.validateUsername(registrationDTO.getDisplayName())) {
            throw new BadInputException("Invalid display name!");
        }
        if(!userValidator.validateEmail(registrationDTO.getEmail())) {
            throw new BadInputException("The provided email is not correct!");
        }
        if(!userValidator.validatePassword(registrationDTO.getPassword())) {
            throw new BadInputException("Your password doesn't meet the requirements!");
        }
        userRepository.findByEmail(registrationDTO.getEmail()).ifPresent(
                user -> {
                    throw new AlreadyExistsException("A user with this email already exists!");
                }
        );
        User user = User.builder()
                .displayName(registrationDTO.getDisplayName())
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .email(registrationDTO.getEmail())
                .youtubeChannel(registrationDTO.getYoutubeChannel())
                .userType(UserType.valueOf(registrationDTO.getUserType()))
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .build();

        if (registrationDTO.getProfilePicture() != null && !registrationDTO.getProfilePicture().isEmpty()) {
            String filePath = saveFile(registrationDTO.getProfilePicture());
            user.setProfilePicture(filePath);
        }
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return userMapper.entityToDTO(user, jwtToken, refreshToken);
    }

    private String saveFile(MultipartFile file) {
        String uploadDir = "D:/Projects_2024/DonoDash_profile_pictures";
        Path filePath = Paths.get(uploadDir, System.currentTimeMillis() + "_" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + filePath.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("There is no user registered with this email!"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = UserDTO.builder()
                        .jwtToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
