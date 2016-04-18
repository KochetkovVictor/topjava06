package ru.javawebinar.topjava.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * Created by Kochetkov_V on 18.04.2016.
 */
public class RsourceControllerTest extends AbstractControllerTest {
    @Test
    public void resourceTest() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/css")));
    }
}
