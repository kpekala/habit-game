package com.kpekala.habitgame.domain.player.exception;

public class NoItemException extends RuntimeException{
    public NoItemException () {
        super("User does not have this item");
    }
}
