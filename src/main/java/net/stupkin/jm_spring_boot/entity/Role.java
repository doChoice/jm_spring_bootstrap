package net.stupkin.jm_spring_boot.entity;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_role")
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles"
            , joinColumns = @JoinColumn(name = "role_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String role) {
        this.roleName = role;
    }

    public Role(Long id, String role) {
        this.id = id;
        this.roleName = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String role) {
        this.roleName = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }


    @Override
    public String toString() {
        return roleName + " ";
    }
}
