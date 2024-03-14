package com.kpekala.habitgame.domain.shop.exception;

public class NotEnoughGoldException extends RuntimeException{
    public NotEnoughGoldException() {
        super("Not enough gold!");
    }
}
