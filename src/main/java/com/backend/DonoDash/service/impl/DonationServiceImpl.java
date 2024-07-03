package com.backend.DonoDash.service.impl;

import com.backend.DonoDash.dto.DonationDTO;
import com.backend.DonoDash.enums.DonationSource;
import com.backend.DonoDash.model.Donation;
import com.backend.DonoDash.model.User;
import com.backend.DonoDash.persistence.DonationRepository;
import com.backend.DonoDash.persistence.UserRepository;
import com.backend.DonoDash.service.interfaces.DonationService;
import com.backend.DonoDash.utils.mapper.DonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final DonationMapper donationMapper;

    @Override
    public List<DonationDTO> getUserDonations(UUID id) {
        List<Donation> donations = donationRepository.findDonationsByUser(id);
        return donations.stream().map(donationMapper::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public void saveRevolutDonations(List<DonationDTO> donations) {
        for (DonationDTO dto : donations) {
            Donation donation = new Donation();
            donation.setAmount(dto.getAmount());
            donation.setSource(DonationSource.REVOLUT);
            donation.setCurrency(dto.getCurrency());
            donation.setDonor_handle(dto.getDonor());
            donation.setDonor_full_name(dto.getDonor_full_name());
            donation.setMessage(dto.getMessage());
            donation.setDate_received(dto.getDate_received());

            Optional<User> userOptional = userRepository.findById(UUID.fromString("bc360180-38cc-4fc8-9ca1-84288083c834"));
            if (userOptional.isPresent()) {
                donation.setUser(userOptional.get());
            } else {
                System.out.println("User not found for the given ID.");
                continue;
            }

            Optional<Donation> existingDonation = donationRepository.findDonationByDonor_full_name(donation.getDonor_full_name());
            if (existingDonation.isEmpty()) {
                donationRepository.save(donation);
            }
        }
    }
}
