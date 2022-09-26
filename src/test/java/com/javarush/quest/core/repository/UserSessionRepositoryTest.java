package com.javarush.quest.core.repository;

import com.javarush.quest.core.entity.UserSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserSessionRepositoryTest {

    private static final String USER_SESSION_ID = UUID.randomUUID().toString();

    @Test
    public void save() {
        UserSessionRepository userSessionRepository = new UserSessionRepository();
        UserSession userSession = UserSession
                .builder()
                .sessionId(USER_SESSION_ID)
                .build();
        userSessionRepository.save(USER_SESSION_ID, userSession);
        assertEquals(userSession, userSessionRepository.getById(USER_SESSION_ID));
    }

    @Test
    public void saveAll() {
        UserSessionRepository userSessionRepository = new UserSessionRepository();
        Map<String, UserSession> map = new HashMap<>();
        UserSession userSession1 = UserSession
                .builder()
                .sessionId("1")
                .build();
        UserSession userSession2 = UserSession
                .builder()
                .sessionId("2")
                .build();
        map.put("1", userSession1);
        map.put("2", userSession2);

        userSessionRepository.saveAll(map);
        assertEquals(userSession1, userSessionRepository.getById("1"));
        assertEquals(userSession2, userSessionRepository.getById("2"));
    }

    @Test
    public void getById() {
        UserSessionRepository userSessionRepository = new UserSessionRepository();
        UserSession userSession = UserSession
                .builder()
                .sessionId(USER_SESSION_ID)
                .build();
        userSessionRepository.save(USER_SESSION_ID, userSession);
        assertEquals(userSession, userSessionRepository.getById(USER_SESSION_ID));
    }

    @Test
    public void isExists() {
        UserSessionRepository userSessionRepository = new UserSessionRepository();
        UserSession userSession = UserSession
                .builder()
                .sessionId(USER_SESSION_ID)
                .build();
        userSessionRepository.save(USER_SESSION_ID, userSession);
        assertTrue(userSessionRepository.isExists(USER_SESSION_ID));
    }

    @Test
    public void isEmpty() {
        UserSessionRepository userSessionRepository = new UserSessionRepository();
        assertTrue(userSessionRepository.isEmpty());
    }
}