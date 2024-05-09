package com.backend.DonoDash.controller;

import com.backend.DonoDash.dto.AuthenticationDTO;
import com.backend.DonoDash.dto.RegistrationDTO;
import com.backend.DonoDash.dto.UserDTO;
import com.backend.DonoDash.persistence.TokenRepository;
import com.backend.DonoDash.service.interfaces.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/donodash/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final UserService userService;
    private final TokenRepository tokenRepository;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<UserDTO> register(@RequestParam("firstName") String firstName,
                                            @RequestParam("lastName") String lastName,
                                            @RequestParam("displayName") String displayName,
                                            @RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("youtubeChannel") String youtubeChannel,
                                            @RequestParam("userType") String userType,
                                            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {
        RegistrationDTO registrationDTO = new RegistrationDTO(firstName, lastName, displayName, youtubeChannel, profilePicture, email, password, userType);
        return ResponseEntity.ok(userService.register(registrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) {
        UserDTO user = userService.login(authenticationDTO);

        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", user.getJwtToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", user.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        userService.refreshToken(httpServletRequest, httpServletResponse);
    }

    @GetMapping("/validate-session")
    public ResponseEntity<?> validateSession(HttpServletRequest request) {
        String jwt = extractJwtFromCookie(request, "accessToken");
        System.out.println(jwt);
        if (jwt != null && !jwt.isEmpty()) {
            return tokenRepository.findByToken(jwt)
                    .map(token -> {
                        if (!token.isExpired() && !token.isRevoked()) {
                            return ResponseEntity.ok().build(); // Token is valid
                        }
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Token is expired or revoked
                    })
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // Token not found
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String extractJwtFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
