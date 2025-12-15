package com.openstage.ticketbook.service;

import com.openstage.ticketbook.dto.ContactDTO;
import com.openstage.ticketbook.model.Contact;
import com.openstage.ticketbook.model.User;
import com.openstage.ticketbook.repo.ContactRepo;
import com.openstage.ticketbook.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepo contactRepo;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    public Contact createContact(ContactDTO contactDTO) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER_ID") == null) {
            throw new RuntimeException("Error: User not logged in.");
        }
        Long userId = (Long) session.getAttribute("USER_ID");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        Contact contact = Contact.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .message(contactDTO.getMessage())
                .build();
        return contactRepo.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    public void deleteContact(Long id) {
        contactRepo.deleteById(id);
    }
}
