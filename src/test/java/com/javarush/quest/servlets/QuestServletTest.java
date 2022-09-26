package com.javarush.quest.servlets;

import com.javarush.quest.core.entity.User;
import com.javarush.quest.core.entity.UserSession;
import com.javarush.quest.core.repository.QuestStepRepository;
import com.javarush.quest.core.repository.UserRepository;
import com.javarush.quest.core.repository.UserSessionRepository;
import com.javarush.quest.core.util.QuestInitializer;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class QuestServletTest {

    private ServletContext servletContext = mock(ServletContext.class);
    private ServletConfig config = mock(ServletConfig.class);
    private QuestStepRepository questStepRepository;
    private UserRepository userRepository;
    private UserSessionRepository userSessionRepository;
    private User user;
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USER_SESSION_ID = UUID.randomUUID().toString();

    @Test
    public void doGet() throws ServletException, IOException {
        QuestServlet questServlet = new QuestServlet();

        User user = User
                .builder()
                .username("testUser")
                .build();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/WEB-INF/quest.jsp")).thenReturn(dispatcher);

        questServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test // should redirect to "/quest" with next quest step
    public void doPost() throws IOException, ServletException {
        QuestServlet questServlet = new QuestServlet();
        prepareData(true);

        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userRepository")).thenReturn(userRepository);
        when(servletContext.getAttribute("questStepRepository")).thenReturn(questStepRepository);
        when(servletContext.getAttribute("userSessionRepository")).thenReturn(userSessionRepository);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("nextStep")).thenReturn("intro_2");
        when(httpSession.getId()).thenReturn(USER_SESSION_ID);

        questServlet.init(config);
        questServlet.doPost(request, response);

        verify(response).sendRedirect("quest");
    }

    @Test // should redirect to "/"
    public void doPostWithoutUserSession() throws IOException, ServletException {
        QuestServlet questServlet = new QuestServlet();
        prepareData(false);

        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userRepository")).thenReturn(userRepository);
        when(servletContext.getAttribute("questStepRepository")).thenReturn(questStepRepository);
        when(servletContext.getAttribute("userSessionRepository")).thenReturn(userSessionRepository);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("nextStep")).thenReturn("intro_2");
        when(httpSession.getId()).thenReturn(USER_SESSION_ID);

        questServlet.init(config);
        questServlet.doPost(request, response);

        verify(response).sendRedirect("/");
    }

    @Test // should redirect to "/end"
    public void doPostToEndPage() throws IOException, ServletException {
        QuestServlet questServlet = new QuestServlet();
        prepareData(false);

        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userRepository")).thenReturn(userRepository);
        when(servletContext.getAttribute("questStepRepository")).thenReturn(questStepRepository);
        when(servletContext.getAttribute("userSessionRepository")).thenReturn(userSessionRepository);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("nextStep")).thenReturn("qwerty");
        when(httpSession.getId()).thenReturn(USER_SESSION_ID);

        questServlet.init(config);
        questServlet.doPost(request, response);

        verify(response).sendRedirect("end");
    }

    private void prepareData(boolean createUserSession) {
        servletContext = mock(ServletContext.class);
        config = mock(ServletConfig.class);
        questStepRepository = new QuestStepRepository();
        userRepository = new UserRepository();
        userSessionRepository = new UserSessionRepository();
        questStepRepository.saveAll(new QuestInitializer().getQuestSteps());
        createUser();
        if (createUserSession) {
            createUserSession();
        }

    }

    private void createUser() {
        user = User
                .builder()
                .username("testUser")
                .currentQuestStep(questStepRepository.getById("intro_1"))
                .build();
        userRepository.save(USER_ID, user);
    }

    private void createUserSession() {
        UserSession userSession = UserSession
                .builder()
                .sessionId(USER_SESSION_ID)
                .user(user)
                .build();
        userSessionRepository.save(userSession.getSessionId(), userSession);
    }


}