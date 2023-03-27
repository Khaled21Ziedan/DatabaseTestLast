package com.example.Database;

import com.example.Database.adapter.model.User;
import com.example.Database.adapter.rest.UserController;
import com.example.Database.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @MockBean
    private UserService userService;
    User user = User.builder()
            .id(1l)
            .name("ahmad")
            .email("a@123")
            .age(19)
            .active(true)
            .city("amman")
            .build();

    @Test
    public void getUsersTest() throws Exception {
        List<User> users = Arrays.asList(user);
        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/allUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("ahmad")));

    }

    @Test
    public void getUserByIdTest() throws Exception {
        Mockito.when(userService.loadUserById(user.getId())).thenReturn(Optional.ofNullable(user));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("ahmad")));
    }

    @Test
    public void saveUserTest() throws Exception {
        User user = User.builder()
                .id(4l)
                .name("ahmad")
                .email("a@123")
                .age(19)
                .active(true)
                .city("amman")
                .build();

        Mockito.when(userService.registerUser(user)).thenReturn(user.getId());
        String content = objectWriter.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc
                .perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }

    @Test
    public void updateUserTest() throws Exception {
        User updateUser = User.builder()
                .id(4l)
                .name("ahmad")
                .email("a@123")
                .age(19)
                .active(true)
                .city("amman")
                .build();

        Mockito.when(userService.update(updateUser)).thenReturn(updateUser);

        String updatedContent = objectWriter.writeValueAsString(updateUser);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc
                .perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }
    @Test
    public void removeUserTest() throws Exception {

        Mockito.when(userService.loadUserById(user.getId())).thenReturn(Optional.ofNullable(user));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/delete/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(mockRequest)
                .andExpect(status().isOk());

    }
}