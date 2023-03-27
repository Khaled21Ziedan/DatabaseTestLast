package com.example.Database;

import com.example.Database.adapter.model.User;
import com.example.Database.adapter.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Before
    public void setup(){
        userRepository.save(new User(1l,"amer","a@a.com","amman",23,true));
        userRepository.save(new User(2l,"samer","s@s.com","amman",23,true));
    }
    @After
    public void destroy(){
        userRepository.removeUser(1l);
        userRepository.removeUser(2l);
    }
    @Test
    public void getValidUserById_Test(){
        User saved = new User(35l,"sameh","r@r","amman",33,true);
        userRepository.save(saved);
        userRepository.getById(35l);
        Assertions.assertThat(saved.getAge()).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNegative();
        Assertions.assertThat(saved.getId()).isGreaterThan(0);
    }
    @Test
    public void findAllUsers_Test(){
        List<User> users = userRepository.findAll();
        Assertions.assertThat(users.size()).isEqualTo(2);
        Assertions.assertThat(users.get(0).getId()).isNotNegative();
        Assertions.assertThat(users.get(0).getId()).isGreaterThan(0);
    }
    @Test
    public void getInvalidUserById_Test() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.getById(120L).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }
}
