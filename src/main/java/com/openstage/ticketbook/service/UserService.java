package com.openstage.ticketbook.service;

import com.openstage.ticketbook.dto.UserRequestDTO;
import com.openstage.ticketbook.model.User;
import com.openstage.ticketbook.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // Get All Users(ROLE_ADMIN)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User by ID(ROLE_ADMIN)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update User by ID(ROLE_ADMIN,ROLE_USER)
    public Optional<User> updateUser(Long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userRequestDTO.getUsername());
            user.setEmail(userRequestDTO.getEmail());
            return userRepository.save(user);
        });
    }

    // Delete User by ID(ROLE_ADMIN,ROLE_USER)
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
