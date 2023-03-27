package com.example.Database.adapter.repo;

import com.example.Database.adapter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public long save (User user);
    public Optional<User> getById(Long id);
    public List<User> findAll();
    public void removeUser(Long id);

    public User update(User user);
}
