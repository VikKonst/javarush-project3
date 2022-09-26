package com.javarush.quest.servlets;

import com.javarush.quest.core.entity.User;
import com.javarush.quest.core.repository.QuestStepRepository;
import com.javarush.quest.core.repository.UserRepository;
import com.javarush.quest.core.repository.UserSessionRepository;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class EntranceServletTest {

    private final UserRepository userRepository = new UserRepository();
    private final QuestStepRepository questStepRepository = new QuestStepRepository();
    private final UserSessionRepository userSessionRepository = new UserSessionRepository();

    @Test
    public void init() throws ServletException {
        EntranceServlet entranceServlet = new EntranceServlet();
        ServletContext servletContext = mock(ServletContext.class);
        servletContext.setAttribute("userRepository", userRepository);
        servletContext.setAttribute("questStepRepository", questStepRepository);
        servletContext.setAttribute("userSessionRepository", userSessionRepository);

        ServletConfig config = mock(ServletConfig.class);

        when(config.getServletContext()).thenReturn(servletContext);

        entranceServlet.init(config);

        verify(config, times(1)).getServletContext();
    }

    @Test // should redirect if http session contains attribute "user"
    public void doPost() throws IOException {
        EntranceServlet entranceServlet = new EntranceServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);

        when(httpSession.getAttribute("user")).thenReturn(new User());
        when(request.getSession()).thenReturn(httpSession);

        entranceServlet.doPost(request, response);

        verify(response).sendRedirect("quest");
    }

    @Test // should create new user and redirect if http session not contains attribute "user"
    public void doPost2() throws ServletException, IOException {
        EntranceServlet entranceServlet = new EntranceServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);
        ServletConfig config = mock(ServletConfig.class);

        when(servletContext.getAttribute("userRepository")).thenReturn(userRepository);
        when(servletContext.getAttribute("questStepRepository")).thenReturn(questStepRepository);
        when(servletContext.getAttribute("userSessionRepository")).thenReturn(userSessionRepository);
        when(request.getSession()).thenReturn(httpSession);
        when(config.getServletContext()).thenReturn(servletContext);

        entranceServlet.init(config);
        entranceServlet.doPost(request, response);

        verify(response).sendRedirect("quest");
    }


}