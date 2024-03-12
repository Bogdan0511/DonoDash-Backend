package com.backend.DonoDash.dto;

import com.backend.DonoDash.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String youtubeChannel;
    private String profilePicture;
    private String email;
    private UserType userType;
    @JsonProperty("access_token")
    private String jwtToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
