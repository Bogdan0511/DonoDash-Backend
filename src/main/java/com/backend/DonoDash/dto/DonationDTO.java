package com.backend.DonoDash.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationDTO {
    private UUID id;
    private float amount;
    private String source;
    private String currency;
    private String donor;
    private String donor_full_name;
    private String message;
    private LocalDate date_received;
}
