package com.backend.DonoDash.model;

import com.backend.DonoDash.enums.DonationSource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donation")
@Entity
public class Donation extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DonationSource source;

    private float amount;
    private String currency;
    private String donor_handle;
    private String donor_full_name;
    private String message;
    private LocalDate date_received;
}
