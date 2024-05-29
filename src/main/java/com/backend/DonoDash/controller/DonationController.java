package com.backend.DonoDash.controller;

import com.backend.DonoDash.dto.DonationDTO;
import com.backend.DonoDash.service.interfaces.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("donodash/donations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<DonationDTO>> getDonations(@PathVariable UUID userId) {
        return ResponseEntity.ok(donationService.getUserDonations(userId));
    }

}
