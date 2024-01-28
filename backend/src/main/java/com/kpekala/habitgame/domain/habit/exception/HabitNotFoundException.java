package com.kpekala.habitgame.domain.habit.exception;

public class HabitNotFoundException extends RuntimeException{
    public HabitNotFoundException() {
        super("Habit not found!");
    }
}
