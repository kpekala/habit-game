package com.kpekala.habitgame.domain.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Geolocation {

    @Id
    @GeneratedValue
    private Integer id;

    private Float longitude;

    private Float latitude;

    private String place;
}
