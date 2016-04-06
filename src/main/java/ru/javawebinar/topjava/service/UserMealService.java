package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    default Collection<UserMeal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    Collection<UserMeal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    Collection<UserMeal> getAll(int userId);

    UserMeal update(UserMeal meal, int userId) throws NotFoundException;

    UserMeal save(UserMeal meal, int userId);

    Map<UserMeal, User> getMealWithUser(int mealId);
}
