package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
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

    @OneToOne(cascade = CascadeType.ALL)
    private Geolocation location;

    private boolean completed;

    private String photoId;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}
