package com.openstage.ticketbook.service;

import com.openstage.ticketbook.dto.UserRequestDTO;
import com.openstage.ticketbook.dto.UserResponseDTO;
import com.openstage.ticketbook.model.User;
import com.openstage.ticketbook.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // Get All Users(ROLE_ADMIN)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.isActive(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // Get User by ID(ROLE_ADMIN)
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(user -> new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt()
        ));
    }

    // Update User by ID(ROLE_ADMIN,ROLE_USER)
    public Optional<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userRequestDTO.getUsername());
            user.setEmail(userRequestDTO.getEmail());
            User savedUser = userRepository.save(user);
            return new UserResponseDTO(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.isActive(),
                    savedUser.getCreatedAt()
            );
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
