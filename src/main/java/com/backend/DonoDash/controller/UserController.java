package com.backend.DonoDash.controller;

import com.backend.DonoDash.dto.RegistrationDTO;
import com.backend.DonoDash.dto.UpdateDTO;
import com.backend.DonoDash.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/donodash/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @PutMapping(value = "/update/{userId}", consumes = {"multipart/form-data"})
    public ResponseEntity<UpdateDTO> updateUser(@PathVariable UUID userId,
                                                @RequestParam("firstName") String firstName,
                                                @RequestParam("lastName") String lastName,
                                                @RequestParam("displayName") String displayName,
                                                @RequestParam("email") String email,
                                                @RequestParam("youtubeChannel") String youtubeChannel,
                                                @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {
        RegistrationDTO registrationDTO = new RegistrationDTO(firstName, lastName, displayName, youtubeChannel, profilePicture, email, "", "");
        var updateDTO = userService.updateUser(userId, registrationDTO);
        return ResponseEntity.ok(updateDTO);
    }

}
