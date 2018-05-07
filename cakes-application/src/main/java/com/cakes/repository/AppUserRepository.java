package com.cakes.repository;

import com.cakes.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class provides database operation under User object
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findOneByUsername(String username);
}
