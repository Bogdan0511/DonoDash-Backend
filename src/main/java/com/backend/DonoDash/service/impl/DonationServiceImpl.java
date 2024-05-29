package com.backend.DonoDash.service.impl;

import com.backend.DonoDash.dto.DonationDTO;
import com.backend.DonoDash.model.Donation;
import com.backend.DonoDash.persistence.DonationRepository;
import com.backend.DonoDash.service.interfaces.DonationService;
import com.backend.DonoDash.utils.mapper.DonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;

    @Override
    public List<DonationDTO> getUserDonations(UUID id) {
        List<Donation> donations = donationRepository.findDonationsByUser(id);
        return donations.stream().map(donationMapper::entityToDTO).collect(Collectors.toList());
    }
}
