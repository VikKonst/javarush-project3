package com.javarush.quest.core.util;

import com.javarush.quest.core.entity.QuestStep;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class QuestInitializerTest {

    @Test
    public void getQuestSteps() {
        QuestInitializer questInitializer = new QuestInitializer();
        Map<String, QuestStep> questSteps = questInitializer.getQuestSteps();
        assertEquals(35, questSteps.size());
    }
}