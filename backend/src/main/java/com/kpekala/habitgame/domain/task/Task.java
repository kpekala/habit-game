package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    private String title;

    private String description;

    private Difficulty difficulty;

    private Date deadline;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public float getExperience() {
        return switch (difficulty) {
            case EASY -> 5f;
            case MEDIUM -> 10f;
            case HARD -> 20f;
        };
    }

    public float getGold() {
        return switch (difficulty) {
            case EASY -> 10f;
            case MEDIUM -> 25f;
            case HARD -> 50f;
        };
    }
}
