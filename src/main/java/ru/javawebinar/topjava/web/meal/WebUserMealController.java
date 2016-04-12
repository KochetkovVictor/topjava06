package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Kochetkov_V on 12.04.2016.
 */
@Controller
@RequestMapping(value="/meals")
public class WebUserMealController {
    @Autowired
    private UserMealService mealService;

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                        startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, LoggedUser.id()),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, LoggedUser.getCaloriesPerDay()
        ));
        return "mealList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrUpdate(@ModelAttribute("meal") UserMeal meal, HttpServletRequest request)
    {
        final UserMeal userMeal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (userMeal.isNew()) {
            userMeal.setId(null);
            mealService.save(userMeal, LoggedUser.id());
        } else {
            userMeal.setId(Integer.valueOf(request.getParameter("id")));
            mealService.save(userMeal, LoggedUser.id());
        }
        return "mealList";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String mealList(Model model) {
        int userId = LoggedUser.id();
        model.addAttribute("mealList", UserMealsUtil.getWithExceeded(mealService.getAll(userId), LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String toCreatePage(Model model) {
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(value = "/update&id={id}", method = RequestMethod.GET)
    public String toUpdatePage(@PathVariable("id") int mealId, Model model) {
        int userId = LoggedUser.id();
        model.addAttribute("meal", mealService.get(mealId, userId));
        return "mealEdit";
    }

    @RequestMapping(value = "/delete&id={id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int mealId) {
        int userId = LoggedUser.id();
        mealService.delete(mealId, userId);
        return "redirect:/meals";
    }
}


