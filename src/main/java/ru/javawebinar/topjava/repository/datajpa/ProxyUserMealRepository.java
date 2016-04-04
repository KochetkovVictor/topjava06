package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal,Integer> {

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=?1 ORDER BY um.dateTime DESC ")
    List<UserMeal> findAll(int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.id=?1 AND um.user.id=?2")
    UserMeal findOne(int id, int userId);


    @Transactional
    @Modifying
    @Query("SELECT um FROM UserMeal um WHERE um.user.id=?1")
    UserMeal save(UserMeal userMeal, int userId);


    @Transactional
    @Modifying
    @Query("DELETE from UserMeal um WHERE um.id=?1 AND um.user.id=?2")
    int delete(int id, int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=?3 AND um.dateTime>=?1 AND um.dateTime<=?2 ORDER BY um.dateTime DESC")
    List<UserMeal> getBetween(LocalDateTime startDate,LocalDateTime endDate, int userId);
}
