package com.example.Database;

import com.example.Database.adapter.model.User;
import com.example.Database.adapter.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Before // before all
    public void setup(){
        userRepository.save(new User(1l,"amer","a@a.com","amman",23,true));
        userRepository.save(new User(2l,"samer","s@s.com","amman",23,true));
    }
    @Test
    public void findAllUsers_Test(){
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getId()).isNotNegative();
        assertThat(users.get(0).getId()).isGreaterThan(0);
    }
    @Test
    public void getValidUserById_Test(){
        Optional <User> getByIdUser = userRepository.getById(1l);
        assertThat(getByIdUser.map(User::getId)).isNotNull();
        assertThat(getByIdUser.map(User::getId)).isEqualTo(Optional.of(1l));
    }
    @Test
    public void getInvalidUserById_Test(){
        Optional <User> getByIdUser = userRepository.getById(120l);
        assertThat(getByIdUser).isEmpty();
    }
    @Test
    public void removeNotFoundUser() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userRepository.removeUser(120l);
        });
        assertThat(exception).isNotNull();
        assertThat(exception.getClass()).isEqualTo(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("user not found");
    }
    @Test(expected = RuntimeException.class)
    public void removeNotFoundUser2() {
        userRepository.removeUser(120l);
    }
}
