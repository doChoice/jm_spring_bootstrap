package net.stupkin.jm_spring_boot.service;

import net.stupkin.jm_spring_boot.entity.Role;
import net.stupkin.jm_spring_boot.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    User getUserByEmail(String email);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
}
