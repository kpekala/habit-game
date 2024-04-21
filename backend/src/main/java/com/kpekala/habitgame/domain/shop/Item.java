package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    private Float cost;

    private String name;

    private String description;

    private Float healthIncrease;

    @ManyToMany(mappedBy = "items")
    private List<Player> players;
}
