package net.stupkin.jm_spring_boot.dao;


import net.stupkin.jm_spring_boot.entity.Role;
import net.stupkin.jm_spring_boot.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class JpaUserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        Query query = entityManager.createQuery("delete from User where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String emailForUsername) {
        User user = entityManager.createQuery("select u from User u where email = :email", User.class)
                .setParameter("email", emailForUsername)
                .getSingleResult();
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
