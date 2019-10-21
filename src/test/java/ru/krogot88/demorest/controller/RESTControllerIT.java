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
import ru.krogot88.demorest.dto.WordFourDTO;
import ru.krogot88.demorest.model.Word;


import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
    public void getRandomWordFourDTOTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;

        mvcResult = mockMvc.perform(get("/word/random/game")).andExpect(status().isNoContent()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        Word word = new Word("four","четыре");
        wordRepository.save(word);
        mvcResult = mockMvc.perform(get("/word/random/game")).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        WordFourDTO resultWordFourDTO = objectMapper.readValue(content,WordFourDTO.class);
        Set<String> translateSet = new HashSet<>();
        translateSet.add(resultWordFourDTO.getTranslate1());
        translateSet.add(resultWordFourDTO.getTranslate2());
        translateSet.add(resultWordFourDTO.getTranslate3());
        translateSet.add(resultWordFourDTO.getTranslate4());
        Assert.assertTrue(translateSet.size() == 4);
    }

    @Test
    public void getRandomWordTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;

        Set<String> set = new HashSet<>();
        for(int i = 0 ; i < 12;i++) {
            mvcResult = mockMvc.perform(get("/word/random")).andExpect(status().isOk()).andReturn();
            content = mvcResult.getResponse().getContentAsString();
            Word resultWord = objectMapper.readValue(content, Word.class);
            set.add(resultWord.getName());
        }
        Assert.assertTrue(set.size() == 3);

        wordRepository.deleteById(3L);
        wordRepository.deleteById(2L);
        wordRepository.deleteById(1L);
        mvcResult = mockMvc.perform(get("/word/random")).andExpect(status().isNoContent()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));
    }

    @Test
    public void getWordByIdTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;

        mockMvc.perform(get("/word/id/1")).andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/word/id/40")).andExpect(status().isNotFound()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        mvcResult = mockMvc.perform(get("/word/id/notLongID")).andExpect(status().isBadRequest()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals("{\"id\":\"" +
                ms.getMessage("id.not.digits",null, Locale.getDefault()) + "\"}"));

        mvcResult = mockMvc.perform(get("/word/id/3")).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertTrue(resultWord.getName().equals("three"));
        Assert.assertTrue(resultWord.getTranslate().equals("три"));
    }

    @Test
    public void getWordByNameTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;

        mockMvc.perform(get("/word/name/one")).andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/word/name/four")).andExpect(status().isNotFound()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.equals(""));

        mvcResult = mockMvc.perform(get("/word/name/three")).andExpect(status().isOk()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertTrue(resultWord.getName().equals("three"));
        Assert.assertTrue(resultWord.getTranslate().equals("три"));
    }

    @Test
    public void saveNewWordTest() throws Exception {
        MvcResult mvcResult = null;
        String content = null;
        String json = null;

        json = "{\"name\":\"four\",\"translate\": \"четыре\"}";
        mvcResult = mockMvc.perform(post("/word").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                           .andExpect(status().isCreated())
                           .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        Word resultWord = objectMapper.readValue(content,Word.class);
        Assert.assertTrue(resultWord.getId() == 4);
        Assert.assertTrue(resultWord.getName().equals("four"));
        Assert.assertTrue(resultWord.getTranslate().equals("четыре"));

        json = "{\"name\":\"one\",\"translate\": \"один\"}";
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
