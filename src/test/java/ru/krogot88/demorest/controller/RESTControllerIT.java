package ru.krogot88.demorest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.dto.WordGameDTO;
import ru.krogot88.demorest.model.Word;


import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RESTControllerIT {

    @Autowired
    private MessageSource ms;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WordRepository wordRepository;

    @Before
    public  void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void getRandomWordGameDTOTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;
        Long rowsInTableWords = wordRepository.count();

        mvcResult = mockMvc.perform(get("/word/random/game/" + (rowsInTableWords + 1))).andExpect(status().isNoContent()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        mvcResult = mockMvc.perform(get("/word/random/game/" + rowsInTableWords)).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        WordGameDTO resultWordGameDTO = objectMapper.readValue(content, WordGameDTO.class);
        Set<String> translateSet = new HashSet<>();
        for(String translate : resultWordGameDTO.getTranslates()) {
            translateSet.add(translate);
        }
        Assert.assertTrue(translateSet.size() == rowsInTableWords);
    }

    @Test
    public void getRandomWordTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;

        Set<String> set = new HashSet<>();
        while (set.size() != wordRepository.count()) {
            mvcResult = mockMvc.perform(get("/word/random")).andExpect(status().isOk()).andReturn();
            content = mvcResult.getResponse().getContentAsString();
            Word resultWord = objectMapper.readValue(content, Word.class);
            set.add(resultWord.getName());
        }

        wordRepository.deleteAll();
        mvcResult = mockMvc.perform(get("/word/random")).andExpect(status().isNoContent()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));
    }

    @Test
    public void getWordByIdTest() throws Exception {
        final Long randomIndexInTableWords = ThreadLocalRandom.current().nextLong(1,wordRepository.count());
        final Long randomIndexNotInTableWords = ThreadLocalRandom.current().nextLong(wordRepository.count() + 1,Long.MAX_VALUE);
        final String notLongType = "78456dhe574d5h";
        Word wordStraightFromDatabase = wordRepository.findById(randomIndexInTableWords).get();
        MvcResult mvcResult = null;
        String content = null;

        mvcResult = mockMvc.perform(get("/word/id/"+randomIndexNotInTableWords)).andExpect(status().isNotFound()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        mvcResult = mockMvc.perform(get("/word/id/" + notLongType)).andExpect(status().isBadRequest()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"id\":\"" +
                ms.getMessage("id.not.digits",null, Locale.getDefault()) + "\"}"));

        mvcResult = mockMvc.perform(get("/word/id/" + randomIndexInTableWords)).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertEquals(wordStraightFromDatabase.getName(),resultWord.getName());
        Assert.assertEquals(wordStraightFromDatabase.getTranslate(),resultWord.getTranslate());
    }

    @Test
    public void getWordByNameTest() throws Exception {
        final String randomWordNameThatExistsInDatabase = wordRepository.getRandomWord().getName();
        final String randomWordNameThatNotExistInDatabase = "kokoko";
        MvcResult mvcResult = null;
        String content = null;

        mvcResult = mockMvc.perform(get("/word/name/" + randomWordNameThatNotExistInDatabase)).andExpect(status().isNotFound()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        mvcResult = mockMvc.perform(get("/word/name/" + randomWordNameThatExistsInDatabase)).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertEquals(randomWordNameThatExistsInDatabase,resultWord.getName());
    }

    @Test
    public void saveNewWordTest() throws Exception {
        final String randomWordNameThatExistsInDatabase = wordRepository.getRandomWord().getName();
        final String randomWordNameThatNotExistInDatabase = "kokoko";
        MvcResult mvcResult = null;
        String content = null;
        String json = null;

        json = "{\"name\":\"" + randomWordNameThatNotExistInDatabase + "\",\"translate\": \"кококо\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                           .andExpect(status().isCreated())
                           .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word wordInJsonResponse = objectMapper.readValue(content,Word.class);
        Word savedWordInDatabase = wordRepository.findByName(randomWordNameThatNotExistInDatabase).get();
        Assert.assertEquals(savedWordInDatabase.getId(),wordInJsonResponse.getId());
        Assert.assertEquals(savedWordInDatabase.getName(),wordInJsonResponse.getName());
        Assert.assertEquals(savedWordInDatabase.getTranslate(),wordInJsonResponse.getTranslate());

        json = "{\"name\":\"" + randomWordNameThatExistsInDatabase + "\",\"translate\": \"неважно\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isConflict())
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        json = "{\"name\":\"on534мкеу5e\",\"translate\": \"один\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"name\":\"" +
                ms.getMessage("word.not.english",null, Locale.getDefault()) + "\"}"));

        json = "{\"name\":\"one\",\"translate\": \"оди23dsb24н\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"translate\":\"" +
                ms.getMessage("word.not.russian",null, Locale.getDefault()) + "\"}"));

        json = "{\"name\":\"o\",\"translate\": \"один\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"name\":\"" +
                ms.getMessage("word.too.short",null, Locale.getDefault()) + "\"}"));

        json = "{\"name\":\"four\",\"translate\": \"\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"translate\":\"" +
                ms.getMessage("translate.not.empty",null, Locale.getDefault()) + "\"}")
                || content.equals("{\"translate\":\"" +
                ms.getMessage("word.not.russian",null, Locale.getDefault()) + "\"}"));
    }


}
