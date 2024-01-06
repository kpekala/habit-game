package com.kpekala.habitgame.domain.user;

import com.kpekala.habitgame.domain.user.dto.UserResponse;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserInformation(String fullName) {
        var userOptional = userRepository.findByFullName(fullName);
        var user = userOptional.orElseThrow(UserNotFoundException::new);

        return prepareUserResponse(user);
    }

    private UserResponse prepareUserResponse(User user) {
        var userResponse = new UserResponse();
        var player = user.getPlayer();

        userResponse.setGold(player.getGold());
        userResponse.setExperience(player.getExperience());
        userResponse.setMaxExperience(100 + player.getLvl() * 10);
        userResponse.setLevel(player.getLvl());

        userResponse.setName(userResponse.getName());
        userResponse.setEmailAddress(userResponse.getEmailAddress());
        userResponse.setCreationTime(user.getCreationDate());

        return userResponse;
    }
}
