package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.LoggedUser;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private UserMealService mealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String posts(@RequestParam(value="action") String action, @ModelAttribute("meal") UserMeal meal,HttpServletRequest request, Model model) {
        if ("filter".equals(action)){
        LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                        startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, LoggedUser.id()),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, LoggedUser.getCaloriesPerDay()
        ));
        }
        else{
            if (meal.getDescription()==null || meal.getDescription().length()==0)
                return "mealEdit";
            else{

                mealService.save(meal,LoggedUser.id());
            }
        }
        return "redirect:/mealList";
    }

    @RequestMapping(value = "/meals")
    public String gets(@RequestParam(value = "action", required = false) String action, Model model, HttpServletRequest request) {
        int userId = LoggedUser.id();
        if (action == null) {
            model.addAttribute("mealList", UserMealsUtil.getWithExceeded(mealService.getAll(userId), LoggedUser.getCaloriesPerDay()));
            return "mealList";
        } else {
            if ("delete".equals(action)) {
                int mealId = Integer.valueOf(request.getParameter("id"));
                mealService.delete(mealId, userId);
                return "redirect:/meals";
            } else {
                UserMeal meal;
                if ("create".equals(action)) {// create
                    meal = new UserMeal(LocalDateTime.now(), "", 1000);
                } else {// update
                    int mealId = Integer.valueOf(request.getParameter("id"));
                    meal = mealService.get(mealId, userId);
                }
                model.addAttribute("meal", meal);
                return "/mealEdit";
            }
        }
    }
}
