package com.kpekala.habitgame.domain.user;

import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.user.dto.UserResponse;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerService playerService;

    @Override
    public UserResponse getUserInformation(String email) {
        var userOptional = userRepository.findByEmailAddress(email);
        var user = userOptional.orElseThrow(UserNotFoundException::new);

        return prepareUserResponse(user);
    }

    private UserResponse prepareUserResponse(User user) {
        var userResponse = new UserResponse();
        var player = user.getPlayer();

        userResponse.setGold(player.getGold());
        userResponse.setExperience(player.getExperience());
        userResponse.setMaxExperience(playerService.calculateMaxExperience(player.getLvl()));
        userResponse.setLevel(player.getLvl());
        userResponse.setHealth(player.getHp());

        userResponse.setName(user.getFullName());
        userResponse.setEmailAddress(user.getEmailAddress());
        userResponse.setCreationTime(user.getCreationDate());

        return userResponse;
    }
}
