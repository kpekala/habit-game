package com.kpekala.habitgame.domain.user;

import com.kpekala.habitgame.domain.user.dto.UserResponse;

public interface UserService {
    UserResponse getUserInformation(String email);
}
