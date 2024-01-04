package com.kpekala.habitgame.player;

import com.kpekala.habitgame.user.UserDetails;
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

    private float hp;

    @OneToOne
    private UserDetails user;

    public Player(float hp) {
        this.hp = hp;
    }
}
