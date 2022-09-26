package com.javarush.quest.core.repository;

import com.javarush.quest.core.entity.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    private static final String USER_ID = UUID.randomUUID().toString();

    @Test
    public void save() {
        UserRepository userRepository = new UserRepository();
        User user = User
                .builder()
                .username("testUser")
                .build();
        userRepository.save(USER_ID, user);
        assertEquals(user, userRepository.getById(USER_ID));
    }

    @Test
    public void saveAll() {
        UserRepository userRepository = new UserRepository();
        User user1 = User
                .builder()
                .username("testUser1")
                .build();
        User user2 = User
                .builder()
                .username("testUser2")
                .build();
        Map<String, User> users = new HashMap<>();
        users.put("1", user1);
        users.put("2", user2);
        userRepository.saveAll(users);
        assertEquals(user1, userRepository.getById("1"));
        assertEquals(user2, userRepository.getById("2"));
    }

    @Test
    public void getById() {
        UserRepository userRepository = new UserRepository();
        User user = User
                .builder()
                .username("testUser")
                .build();
        userRepository.save(USER_ID, user);
        assertEquals(user, userRepository.getById(USER_ID));
    }

    @Test
    public void isExists() {
        UserRepository userRepository = new UserRepository();
        User user = User
                .builder()
                .username("testUser")
                .build();
        userRepository.save(USER_ID, user);
        assertTrue(userRepository.isExists(USER_ID));
    }

    @Test
    public void isEmpty() {
        UserRepository userRepository = new UserRepository();
        assertTrue(userRepository.isEmpty());
    }
}