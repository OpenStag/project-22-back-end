package com.openstage.ticketbook.repo;

import com.openstage.ticketbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);
}
