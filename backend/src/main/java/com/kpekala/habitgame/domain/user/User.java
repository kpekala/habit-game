package com.kpekala.habitgame.domain.user;

import com.kpekala.habitgame.domain.habit.Habit;
import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.Role;
import com.kpekala.habitgame.domain.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String emailAddress;

    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Habit> habits = new ArrayList<>();

    public User(String fullName, String emailAddress, String password) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void removeHabit(Habit habit) {
        habits.remove(habit);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
