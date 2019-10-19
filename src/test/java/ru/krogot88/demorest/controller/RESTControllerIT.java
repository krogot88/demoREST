package ru.krogot88.demorest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.krogot88.demorest.model.Word;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RESTControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }


    @Test
    public void getWordByIdTest() throws Exception {
        mockMvc.perform(get("/word/id/1")).andExpect(status().isOk());
        mockMvc.perform(get("/word/id/40")).andExpect(status().isNotFound());
        mockMvc.perform(get("/word/id/notLongID")).andExpect(status().isBadRequest());

        MvcResult mvcResult = mockMvc.perform(get("/word/id/3")).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertTrue(resultWord.getName().equals("testtest"));
        Assert.assertTrue(resultWord.getTranslate().equals("тесттест"));
    }
}
