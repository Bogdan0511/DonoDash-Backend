package com.backend.DonoDash.persistence;

import com.backend.DonoDash.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {

    @Query(value = """
            select d from Donation d inner join User u\s
            on d.user.id = u.id\s
            where u.id = :id\s
            """)
    List<Donation> findDonationsByUser(UUID id);

    @Query(value = """
            select d from Donation d\s
            where d.donor_full_name = :id\s
            """)
    Optional<Donation> findDonationByDonor_full_name(String id);
}
