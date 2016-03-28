package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    EntityManager em;
    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User u=em.getReference(User.class, userId);
        userMeal.setUser(u);
        if(userMeal.isNew()){
            em.persist(userMeal);
        }
        else{
            if (get(userMeal.getId(),userId)==null) return null;
            else em.merge(userMeal);
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE).setParameter("id",id).setParameter("userId",userId).executeUpdate()!=0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return em.createNamedQuery(UserMeal.GET,UserMeal.class).setParameter("userId",userId).setParameter("id",id).getSingleResult();
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED,UserMeal.class).setParameter("userId",userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN,UserMeal.class).setParameter("userId",userId).setParameter("startDateTime",startDate).
                setParameter("endDateTime",endDate).getResultList();
    }
}