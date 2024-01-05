package com.kpekala.habitgame.domain.role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
