package com.example.Database;

import com.example.Database.adapter.model.User;
import com.example.Database.adapter.repo.UserRepository;
import com.example.Database.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    User user1 = new User(1l,"khaled","k@k.com","amman",23,true);
    User user2 = new User(2l,"mosa","m@m.com","amman",24,true);
    @Test
    public void registerUser_Test(){
        Mockito.when(userRepository.save(user1)).thenReturn(1l);
        Long id = userService.registerUser(user1);
        assertEquals(id, user1.getId());
    }
    @Test
    public void loadUserById_Test(){
        Mockito.when(userRepository.getById(1l)).thenReturn(Optional.ofNullable(user1));
        Optional<User> user = userService.loadUserById(1l);
        assertEquals(user.get().getName(), "khaled");
    }
    @Test
    public void loadUserByIdNotFound_Test(){
        Mockito.when(userRepository.getById(10l)).thenReturn(Optional.empty());
        Optional<User> user = userService.loadUserById(10l);
        assertTrue(user.isEmpty());
    }
    @Test
    public void removeUser_Test(){
        userService.remove(1l);
        verify(userRepository, times(1)).removeUser(1l);
    }

    @Test
    public void findAll_Test(){
        List<User> list = new ArrayList<>();

        list.add(user1);
        list.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(list);

        List<User> UserList = userService.findAll();
        assertEquals(UserList.size(), 2);
        assertEquals(UserList.get(0).getName(), "khaled");
        assertEquals(UserList.get(1).getName(), "mosa");
        assertEquals(UserList.get(0).getAge(), 23);
        assertEquals(UserList.get(1).getAge(), 24);
    }
}
