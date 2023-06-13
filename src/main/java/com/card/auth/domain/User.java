package com.card.auth.domain;


import com.card.auth.dto.UserDto;
import com.card.shared.entity.BaseEntity;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email_address" }, name = "email_address_UNIQUE")})
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(name = "email_address")
    private String username;

    private String password;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    private User(String firstName, String lastName, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = emailAddress;
        this.password = password;
        this.enabled = true;
    }

    public String getUserFullNameFromClient(){
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    public static User createUser(UserDto userDto, PasswordEncoder encoder){
        var user = new User(userDto.firstName(), userDto.lastName(), userDto.email(), encoder.encode(userDto.password()));
        userDto.roles().forEach(role -> user.addRole(new Role(role)));
        return user;
    }

    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), user.getId())
                .append(getFirstName(), user.getFirstName()).append(getLastName(), user.getLastName())
                .append(getUsername(), user.getUsername()).append(getPassword(), user.getPassword()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getFirstName())
                .append(getLastName()).append(getUsername()).append(getPassword()).toHashCode();
    }
}
