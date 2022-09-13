package com.recody.recodybackend.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecodyUserRepository extends JpaRepository<RecodyUser, Long> {
    Optional<RecodyUser> findByEmail(String email);
    RecodyUser getByEmail(String email);
}
