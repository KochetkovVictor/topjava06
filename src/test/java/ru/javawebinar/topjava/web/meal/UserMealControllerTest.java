package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by Kochetkov_V on 18.04.2016.
 */
public class UserMealControllerTest extends AbstractControllerTest {
    @Test
    public void testUserList() throws Exception {
        int testsize;
        if (LoggedUser.id() == USER_ID) {
            testsize = 6;

        } else {
            testsize = 2;
        }
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(testsize)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL1_ID)),
                                hasProperty("description", is(USER_MEALS.get(USER_MEALS.size() - 1).getDescription()))
                        )
                )));
    }

    @Test
    public void testDeleteUser() throws Exception {
        for (int i = 2; i < 8; i++) {
            int id = 100000 + i;
            mockMvc.perform(get("/meals/delete?id="+id))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/meals"));
        }
    }
    @Test
    public void testEditForUpdate() throws Exception {
        for (int i = 2; i < 8; i++) {
            int id=100000+i;
            mockMvc.perform(get("/meals/update?id=" + id))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("meal", hasProperty("id", is(id))))
                    .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"));
        }
    }
    @Test
    public void testEditForCreate() throws Exception {
        mockMvc.perform(get("/meals/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("meal", hasProperty("calories", is(1000))))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"));
    }

    @Test
    public void testUpdateOrCreate() throws Exception {
        mockMvc.perform(post("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(post("/meals/filter"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andReturn();
    }
}
