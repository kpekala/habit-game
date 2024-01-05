package com.kpekala.habitgame.domain.user;

import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String emailAddress;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public User(String fullName, String emailAddress, String password) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
