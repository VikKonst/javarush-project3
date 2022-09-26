package com.javarush.quest.servlets;

import com.javarush.quest.core.entity.QuestStep;
import com.javarush.quest.core.entity.User;
import com.javarush.quest.core.entity.UserSession;
import com.javarush.quest.core.repository.QuestStepRepository;
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

@WebServlet(name="QuestServlet", value="/quest")
public class QuestServlet extends HttpServlet {
    private static final String ENCODING = "utf-8";

    private QuestStepRepository questStepRepository;
    private UserSessionRepository userSessionRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        questStepRepository = (QuestStepRepository) servletContext.getAttribute("questStepRepository");
        userSessionRepository = (UserSessionRepository) servletContext.getAttribute("userSessionRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(ENCODING);
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/quest.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(ENCODING);
        HttpSession httpSession = request.getSession();
        String nextStep = request.getParameter("nextStep");
        QuestStep questStep = questStepRepository.getById(nextStep);
        UserSession userSession = userSessionRepository.getById(httpSession.getId());
        if (questStep == null) {
            response.sendRedirect("end");
        } else if (userSession != null) {
            User user = userSession.getUser();
            user.setCurrentQuestStep(questStep);
            httpSession.setAttribute("user", user);
            response.sendRedirect("quest");
        } else {
            response.sendRedirect("/");
        }
    }
}
