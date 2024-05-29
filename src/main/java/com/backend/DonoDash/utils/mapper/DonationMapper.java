package com.backend.DonoDash.utils.mapper;

import com.backend.DonoDash.dto.DonationDTO;
import com.backend.DonoDash.model.Donation;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {
    public DonationDTO entityToDTO(Donation donation) {
        return DonationDTO.builder()
                .id(donation.getId())
                .amount(donation.getAmount())
                .source(String.valueOf(donation.getSource()))
                .currency(donation.getCurrency())
                .donor(donation.getDonor_handle())
                .donor_full_name(donation.getDonor_full_name())
                .message(donation.getMessage())
                .date_received(donation.getDate_received())
                .build();
    }
}
