package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import org.springframework.http.MediaType;

import ru.javawebinar.topjava.web.json.JsonUtil;

import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDateTime;
import java.util.Arrays;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/rest/meals/";

    @Autowired
    UserMealService userMealService;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(USER_MEALS));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "delete/" + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.contentListMatcher(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);

    }

    @Test
    public void testUpdate() throws Exception {

        UserMeal updated = MEAL1;
        updated.setDescription("Обновленная еда");
        updated.setCalories(1111);
        mockMvc.perform(put(REST_URL + "update/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userMealService.get(MEAL1_ID, LoggedUser.id()));
    }

    @Test
    public void testCreateMeal() throws Exception {
        UserMeal testUserMeal = new UserMeal(null, LocalDateTime.now(), "Тестовая еда", 555);
        ResultActions action = mockMvc.perform(post(REST_URL+"create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(testUserMeal))).andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        testUserMeal.setId(returned.getId());
        MATCHER.assertEquals(testUserMeal, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(testUserMeal,MEAL6, MEAL5, MEAL4, MEAL3, MEAL2,MEAL1),userMealService.getAll(LoggedUser.id()));
    }

    @Test
    public void testGetBetweenRest() throws Exception {
        mockMvc.perform(get(REST_URL + "between?startDateTime=2015-05-30T10:00&endDateTime=2015-05-30T14:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(MEAL2, MEAL1));
    }
}