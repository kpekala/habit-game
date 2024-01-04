package com.kpekala.habitgame.user;

import com.kpekala.habitgame.player.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String emailAddress;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    public UserDetails(String fullName, String emailAddress, String password) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
