package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    private Float gold = 0f;

    @OneToOne(mappedBy = "player")
    private User user;

    public Player(float hp) {
        this.hp = hp;
    }

    public void addGold(float gold) {
        this.gold += gold;
    }

    public void addExp(float exp) {
        experience += exp;

        if (experience >= getMaxExperience()) {
            experience = experience - getMaxExperience();
            lvl++;
        }
    }

    public float getMaxExperience() {
        return lvl * 25;
    }
}
