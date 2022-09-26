package com.javarush.quest.core.listener;

import com.javarush.quest.core.repository.QuestStepRepository;
import com.javarush.quest.core.repository.UserRepository;
import com.javarush.quest.core.repository.UserSessionRepository;
import com.javarush.quest.core.util.QuestInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        QuestInitializer questInitializer = new QuestInitializer();
        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("userRepository", new UserRepository());

        QuestStepRepository questStepRepository = new QuestStepRepository();
        questStepRepository.saveAll(questInitializer.getQuestSteps());

        servletContext.setAttribute("questStepRepository", questStepRepository);

        servletContext.setAttribute("userSessionRepository", new UserSessionRepository());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
    }
}
