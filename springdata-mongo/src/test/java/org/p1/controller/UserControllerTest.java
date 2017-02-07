package org.p1.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@AutoConfigureMockMvc
public class UserControllerTest extends BaseSpringTest {
	@Autowired
    private MockMvc mvc;
	
	
	@Test
    public void getHello() throws Exception {
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/v1/user/p1").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
//                .andExpect(content().json(jsonContent));
    }
}



