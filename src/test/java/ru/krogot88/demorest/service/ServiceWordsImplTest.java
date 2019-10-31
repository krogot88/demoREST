package ru.krogot88.demorest.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.dto.ResponseWrapper;
import ru.krogot88.demorest.dto.WordGameDTO;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.impl.ServiceWordsImpl;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


@RunWith(MockitoJUnitRunner.class)
public class ServiceWordsImplTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private ServiceWord serviceWord = new ServiceWordsImpl();

    @Before
    public void init() {}

    @Test
    public void getRandomWordGameDTOTest() {
        List<Word> databaseList = new ArrayList<>();
        databaseList.add(new Word("one", "один"));
        databaseList.add(new Word("two", "два"));
        databaseList.add(new Word("three", "три"));
        databaseList.add(new Word("four", "четыре"));
        int databaseListSize = databaseList.size();
        Mockito.when(wordRepository.getRandomWord()).then(new Answer<Word>() {
            @Override
            public Word answer(InvocationOnMock invocation) throws Throwable {
                int randomIndex = ThreadLocalRandom.current().nextInt(databaseListSize);
                return databaseList.get(randomIndex);
            }
        });
        Mockito.when(wordRepository.count()).thenReturn(Long.valueOf(databaseListSize));
        ResponseWrapper<WordGameDTO> responseWrapper = null;

        responseWrapper = serviceWord.getRandomWordGameDTO(databaseListSize + 1L);
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NO_CONTENT);

        responseWrapper = serviceWord.getRandomWordGameDTO(Long.valueOf(databaseListSize));
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
        Set<String> translateSet = new HashSet<>();
        WordGameDTO wordGameDTO = responseWrapper.getEntity();
        for (String translate : wordGameDTO.getTranslates()) {
            translateSet.add(translate);
        }
        Assert.assertTrue(translateSet.size() == databaseListSize);
        Mockito.verify(wordRepository, Mockito.atLeast(databaseListSize)).getRandomWord();
    }

    @Test
    public void getRandomWordTest() {
        ResponseWrapper<Word> responseWrapper = null;
        final Long rowsInEmptyTable = 0L;
        final Long rowsInNotEmptyTableExample = ThreadLocalRandom.current().nextLong(1,Long.MAX_VALUE);
        Mockito.when(wordRepository.getRandomWord()).thenReturn(new Word("one", "один"));
        Mockito.when(wordRepository.count()).thenReturn(rowsInEmptyTable,rowsInNotEmptyTableExample);

        responseWrapper = serviceWord.getRandomWord();
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NO_CONTENT);


        responseWrapper = serviceWord.getRandomWord();
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
        Word word = responseWrapper.getEntity();
        Assert.assertTrue(word.getName().equals("one"));
    }

    @Test
    public void getWordById() {
        Mockito.when(wordRepository.findById(1L)).thenReturn(Optional.of(new Word("one", "один")));
        Mockito.when(wordRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseWrapper<Word> responseWrapper = null;

        responseWrapper = serviceWord.getWordById(2L);
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NOT_FOUND);

        responseWrapper = serviceWord.getWordById(1L);
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
        Word word = responseWrapper.getEntity();
        Assert.assertTrue(word.getName().equals("one"));
    }

    @Test
    public void getWordByNameTest() {
        Mockito.when(wordRepository.findByName("one")).thenReturn(Optional.of(new Word("one", "один")));
        Mockito.when(wordRepository.findByName("two")).thenReturn(Optional.empty());
        ResponseWrapper<Word> responseWrapper = null;

        responseWrapper = serviceWord.getWordByName("two");
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NOT_FOUND);

        responseWrapper = serviceWord.getWordByName("one");
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
        Word word = responseWrapper.getEntity();
        Assert.assertTrue(word.getName().equals("one"));
    }

    @Test
    public void saveNewWordTest() {
        Word wordOne = new Word("one", "один");
        Word wordTwo = new Word("two", "два");
        Mockito.when(wordRepository.findByName("one")).thenReturn(Optional.of(wordOne));
        Mockito.when(wordRepository.findByName("two")).thenReturn(Optional.empty());
        Mockito.when(wordRepository.save(wordTwo)).thenAnswer(new Answer<Word>() {
            @Override
            public Word answer(InvocationOnMock invocation) throws Throwable {
                Word result = new Word("two", "два");
                result.setId(ThreadLocalRandom.current().nextLong(1,Long.MAX_VALUE));
                return result;
            }
        });
        ResponseWrapper<Word> responseWrapper = null;

        responseWrapper = serviceWord.saveNewWord(wordOne);
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.CONFLICT);

        responseWrapper = serviceWord.saveNewWord(wordTwo);
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.CREATED);
        Word word = responseWrapper.getEntity();
        Assert.assertTrue(word.getName().equals("two"));
        Assert.assertTrue(word.getId() != null);
    }
}
