package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static ru.javawebinar.topjava.MealTestData.*;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/rest/meals/";

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

    }

    @Test
    public void testCreateMeal() throws Exception {

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