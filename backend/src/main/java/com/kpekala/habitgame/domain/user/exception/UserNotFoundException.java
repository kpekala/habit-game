package com.kpekala.habitgame.domain.user.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException () {
        super("User not found!");
    }
}
