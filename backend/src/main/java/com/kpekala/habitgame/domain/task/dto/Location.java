package com.kpekala.habitgame.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Location {
    private Float latitude;
    private Float longitude;
    private String place;
}
