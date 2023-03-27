package com.example.Database.adapter.repo.impl.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String city;
    private int age ;
    private boolean active ;
}
