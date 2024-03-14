package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.shop.Item;
import com.kpekala.habitgame.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany
    private List<Item> items;

    public Player(float hp) {
        this.hp = hp;
    }

    public void addGold(float gold) {
        this.gold += gold;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
