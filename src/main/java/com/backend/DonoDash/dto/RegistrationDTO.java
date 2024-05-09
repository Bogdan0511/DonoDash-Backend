package com.backend.DonoDash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String displayName;
    private String youtubeChannel;
    private MultipartFile profilePicture;
    private String email;
    private String password;
    private String userType;
}
