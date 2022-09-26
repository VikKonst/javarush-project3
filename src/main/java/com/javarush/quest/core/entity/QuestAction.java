package com.javarush.quest.core.entity;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestAction {
    private String title;
    private String goTo;
}
