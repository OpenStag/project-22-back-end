package com.openstage.ticketbook.repo;

import com.openstage.ticketbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Long> {
}
