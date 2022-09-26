package com.javarush.quest.servlets;

import com.javarush.quest.core.entity.User;
import com.javarush.quest.core.entity.UserSession;
import com.javarush.quest.core.repository.QuestStepRepository;
import com.javarush.quest.core.repository.UserRepository;
import com.javarush.quest.core.repository.UserSessionRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "EntranceServlet", value = "/entrance")
public class EntranceServlet extends HttpServlet {

    private UserRepository userRepository;
    private UserSessionRepository userSessionRepository;
    private QuestStepRepository questStepRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        ServletContext servletContext = config.getServletContext();

        userRepository = (UserRepository) servletContext.getAttribute("userRepository");
        userSessionRepository = (UserSessionRepository) servletContext.getAttribute("userSessionRepository");
        questStepRepository = (QuestStepRepository) servletContext.getAttribute("questStepRepository");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            response.sendRedirect("quest");
            return;
        }

        User user;
        if (userRepository.isExists(username)) {
            user = userRepository.getById(username);
        } else {
            user = createNewUser(username);
            userRepository.save(username, user);
            userSessionRepository.save(session.getId(), new UserSession(user, session.getId()));
        }

        session.setAttribute("user", user);
        response.sendRedirect("quest");
    }

    private User createNewUser(String username) {
        return User
                .builder()
                .username(username)
                .currentQuestStep(questStepRepository.getById("intro_1"))
                .build();
    }

}
