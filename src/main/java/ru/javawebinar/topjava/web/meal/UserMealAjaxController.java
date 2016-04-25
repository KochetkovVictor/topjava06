package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/meals/")
public class UserMealAjaxController extends AbstractUserMealController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("description") String description,
                               @RequestParam("date_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime localDateTime,
                               @RequestParam("calories") int calories) {

        UserMeal userMeal = new UserMeal(id, localDateTime, description, calories);
        if (id == 0) {
            super.create(userMeal);
        } else {
            super.update(userMeal, id);
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getBetween(@RequestParam("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                           @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime startTime,
                           @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime endTime) {
        super.getBetween(startDate, startTime,endDate,endTime);
    }
}
