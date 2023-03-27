package IntegerationTest;

import com.example.Database.DatabaseApplication;
import com.example.Database.adapter.model.User;
import com.example.Database.adapter.repo.UserRepository;
import com.example.Database.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiIntegerationTest {
    @LocalServerPort
    private int port ;
    @Autowired
    private TestRestTemplate restTemplate ;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    private String createURLWithPort() {
        return "http://localhost:" + port + "/users";
    }
    @Test
    @Sql(statements = "INSERT INTO User_Entity(id, name, email, city , age, active) VALUES (1, 'john', 'amman' ,'j@j', 24, true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM User_Entity WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getUserList_Test(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<List<User>> response = restTemplate.exchange(
                createURLWithPort()+"/allUsers", HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                    });

        List<User> userList = response.getBody();
        assert userList != null;
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(userList.size(), userService.findAll().size());
        assertEquals(userList.size(), userRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO User_Entity(id, name, email, city , age, active) VALUES (1, 'john', 'amman' ,'j@j', 24, true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM User_Entity WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getUserById_Test() throws JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Optional<User>> response = restTemplate
                .exchange(createURLWithPort()+"/1", HttpMethod.GET, entity , new ParameterizedTypeReference<>() {
        });
        Optional<User> user = response.getBody();
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(user, userService.loadUserById(1l));
        assertEquals(user, userRepository.getById(1l));
    }
}
