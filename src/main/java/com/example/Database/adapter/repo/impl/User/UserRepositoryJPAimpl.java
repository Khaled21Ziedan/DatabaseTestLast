package com.example.Database.adapter.repo.impl.User;

import com.example.Database.adapter.repo.UserRepository;
import com.example.Database.adapter.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Profile("UserJpa")
public class UserRepositoryJPAimpl implements UserRepository {
    private final UserRepositoryJPASpringImpl userRepositoryJPASpring;

    public UserRepositoryJPAimpl(UserRepositoryJPASpringImpl userRepositoryJPASpring) {
        this.userRepositoryJPASpring = userRepositoryJPASpring;
    }

    @Override
    public long save(User user) {
        UserEntity userEntity = toUserEntity(user);
        UserEntity savedUser = userRepositoryJPASpring.save(userEntity);
        return savedUser.getId();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepositoryJPASpring.findById(id).map(toModel());
    }

    @Override
    public List<User> findAll() {
        return userRepositoryJPASpring.findAll().stream().map(toModel()).collect(Collectors.toList());
    }
    @Override
    public void removeUser(Long id) {
         userRepositoryJPASpring.deleteById(id);
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = toUserEntity(user);
        UserEntity savedUser = userRepositoryJPASpring.save(userEntity);
        return user;
    }

    private UserEntity toUserEntity(User user) {
        return UserEntity
                .builder()
                .active(user.isActive())
                .age(user.getAge())
                .city(user.getCity())
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    private Function<UserEntity, User> toModel() {
        return userEntity -> User.builder()
                .age(userEntity.getAge())
                .active(userEntity.isActive())
                .city(userEntity.getCity())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .name(userEntity.getName())
                .build();
    }
}
