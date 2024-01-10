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
}
