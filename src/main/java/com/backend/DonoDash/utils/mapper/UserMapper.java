package com.backend.DonoDash.utils.mapper;


import com.backend.DonoDash.dto.UserDTO;
import com.backend.DonoDash.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO entityToDTO(User user, String jwtToken, String refreshToken) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .youtubeChannel(user.getYoutubeChannel())
                .displayName(user.getDisplayName())
                .profilePicture(user.getProfilePicture())
                .userType(user.getUserType())
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
