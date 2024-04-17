package com.cfs.obd2logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    @PersistenceContext EntityManager em;
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }
    public User find(Long id) {
        return em.find(User.class, id);
    }
}
