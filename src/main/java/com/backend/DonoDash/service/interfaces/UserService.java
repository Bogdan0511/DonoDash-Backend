package com.backend.DonoDash.service.interfaces;

import com.backend.DonoDash.dto.AuthenticationDTO;
import com.backend.DonoDash.dto.RegistrationDTO;
import com.backend.DonoDash.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {
    UserDTO login(AuthenticationDTO authenticationDTO);
    UserDTO register(RegistrationDTO registrationDTO);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
