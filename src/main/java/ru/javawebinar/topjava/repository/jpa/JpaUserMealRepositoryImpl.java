package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return getAll(userId).get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createQuery("select  meals from UserMeal meals where meals.user.id=:userId").getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}