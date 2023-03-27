package com.example.Database.service;

import com.example.Database.adapter.dto.UserDTO;
import com.example.Database.adapter.repo.UserRepository;
import com.example.Database.adapter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long registerUser(User user) {

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> loadUserById(Long id) {

        return userRepository.getById(id);
    }

    public void remove(Long id) {

        userRepository.removeUser(id);
    }

    public User update(User user) {
        return userRepository.update(user);
    }
}
