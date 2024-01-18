package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Habit {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private boolean isGood;

    private HabitDifficulty habitDifficulty;

    @ManyToOne
    private User user;
}
