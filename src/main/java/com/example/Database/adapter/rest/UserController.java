package com.example.Database.adapter.rest;

import com.example.Database.adapter.dto.UserDTO;
import com.example.Database.adapter.model.User;
import com.example.Database.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Long savingUser(@RequestBody UserDTO userDTO){
        return userService.registerUser(toUser(userDTO));
    }
    @PutMapping
    public UserDTO updatingUser(@RequestBody UserDTO userDTO){
        userService.update(toUser(userDTO));
        return userDTO;
    }
    @GetMapping("/allUsers")
    public List<User> allUsers(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public UserDTO loadingUser(@PathVariable Long id){
        return userService.loadUserById(id).map(toDto()).orElseThrow(()->new RuntimeException("user not found"));
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        userService.loadUserById(id).map(toDto()).orElseThrow(()->new RuntimeException("user not found"));
        userService.remove(id);
    }

    private Function<User,UserDTO> toDto() {
        return user -> UserDTO.builder()
                .age(user.getAge())
                .active(user.isActive())
                .city(user.getCity())
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private User toUser(UserDTO userDTO) {
        return User.builder()
                .age(userDTO.getAge())
                .active(userDTO.isActive())
                .city(userDTO.getCity())
                .email(userDTO.getEmail())
                .id(userDTO.getId())
                .name(userDTO.getName())
                .build();
    }
}
