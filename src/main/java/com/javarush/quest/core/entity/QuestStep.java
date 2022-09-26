package com.javarush.quest.core.entity;

import lombok.*;

import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestStep {
    private String id;
    private String description;
    private Set<QuestAction> actions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestStep questStep = (QuestStep) o;
        return Objects.equals(id, questStep.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
