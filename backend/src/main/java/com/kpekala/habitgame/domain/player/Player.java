package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    private Float hp = 100.0f;

    private Integer lvl = 1;

    private Float experience = 0f;

    @OneToOne
    private User user;

    public Player(float hp) {
        this.hp = hp;
    }
}