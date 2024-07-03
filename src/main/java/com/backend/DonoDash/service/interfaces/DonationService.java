package com.backend.DonoDash.service.interfaces;

import com.backend.DonoDash.dto.DonationDTO;

import java.util.List;
import java.util.UUID;

public interface DonationService {
    List<DonationDTO> getUserDonations(UUID id);
    void saveRevolutDonations(List<DonationDTO> donations);
}
