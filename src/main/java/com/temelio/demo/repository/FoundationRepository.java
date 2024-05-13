package com.temelio.demo.repository;

import com.temelio.demo.entity.Foundation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, UUID> {

    @Transactional(readOnly = true)
    Optional<Foundation> findByNameAndEmail(final String name, final String email);
}

