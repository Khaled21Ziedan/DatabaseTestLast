package com.example.Database.adapter.rest;

import com.example.Database.adapter.dto.UserDTO;
import com.example.Database.adapter.model.User;
import com.example.Database.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long savingUser(@RequestBody User user){
        return userService.registerUser(user);
    }
    @PutMapping
    public User updatingUser(@RequestBody User user){
        userService.update(user);
        return user;
    }
    @GetMapping
    public List<User> allUsers(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public Optional<User> loadingUser(@PathVariable Long id){
        return userService.loadUserById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        userService.loadUserById(id);
        userService.remove(id);
    }
}
