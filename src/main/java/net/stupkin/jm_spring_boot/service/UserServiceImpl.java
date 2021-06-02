package net.stupkin.jm_spring_boot.service;

import net.stupkin.jm_spring_boot.dao.UserDAO;
import net.stupkin.jm_spring_boot.entity.Role;
import net.stupkin.jm_spring_boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String userName) {
        return userDAO.getUserByEmail(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return getUserByUsername(userName);
    }

    @Override
    public Role getRoleById(Long id) {
        return userDAO.getRoleById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return userDAO.getAllRoles();
    }
}
