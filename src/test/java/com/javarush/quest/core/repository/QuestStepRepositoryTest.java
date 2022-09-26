package com.javarush.quest.core.repository;

import com.javarush.quest.core.entity.QuestStep;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class QuestStepRepositoryTest {

    private static final String QUEST_STEP_ID = UUID.randomUUID().toString();

    @Test
    public void save() {
        QuestStepRepository questStepRepository = new QuestStepRepository();

        QuestStep questStep = QuestStep
                .builder()
                .id(QUEST_STEP_ID)
                .build();

        questStepRepository.save(QUEST_STEP_ID, questStep);

        assertEquals(questStep, questStepRepository.getById(QUEST_STEP_ID));
    }

    @Test
    public void saveAll() {
        QuestStepRepository questStepRepository = new QuestStepRepository();
        Map<String, QuestStep> map = new HashMap<>();
        QuestStep questStep1 = QuestStep
                .builder()
                .id("1")
                .build();
        QuestStep questStep2 = QuestStep
                .builder()
                .id("2")
                .build();
        map.put("1", questStep1);
        map.put("2", questStep2);

        questStepRepository.saveAll(map);

        assertEquals(questStep1, questStepRepository.getById("1"));
        assertEquals(questStep2, questStepRepository.getById("2"));
    }

    @Test
    public void getById() {
        QuestStepRepository questStepRepository = new QuestStepRepository();

        QuestStep questStep = QuestStep
                .builder()
                .id(QUEST_STEP_ID)
                .build();

        questStepRepository.save(QUEST_STEP_ID, questStep);

        assertEquals(questStep, questStepRepository.getById(QUEST_STEP_ID));
    }

    @Test
    public void isExists() {
        QuestStepRepository questStepRepository = new QuestStepRepository();

        QuestStep questStep = QuestStep
                .builder()
                .id(QUEST_STEP_ID)
                .build();

        questStepRepository.save(QUEST_STEP_ID, questStep);

        assertTrue(questStepRepository.isExists(QUEST_STEP_ID));
    }

    @Test
    public void isEmpty() {
        QuestStepRepository questStepRepository = new QuestStepRepository();
        assertTrue(questStepRepository.isEmpty());
    }
}