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
import ru.krogot88.demorest.dto.WordFourDTO;
import ru.krogot88.demorest.model.Word;

import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class ServiceWordsImplTest {

    private Long count;

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private ServiceWord serviceWord = new ServiceWordsImpl();

    @Before
    public void init(){
        count = 0L;
    }

    @Test
    public void getRandomWordFourDTOTest() {
        Mockito.when(wordRepository.count()).thenReturn(3L,4L);
        Mockito.when(wordRepository.getRandomWord())
                .thenReturn(new Word("four","четыре"))
                .thenReturn(new Word("two","два"))
                .thenReturn(new Word("four","четыре"))
                .thenReturn(new Word("three","три"))
                .thenReturn(new Word("three","три"))
                .thenReturn(new Word("two","два"))
                .thenReturn(new Word("one","один"));
        ResponseWrapper<WordFourDTO> responseWrapper = null;

        responseWrapper = serviceWord.getRandomWordFourDTO();
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NO_CONTENT);

        responseWrapper = serviceWord.getRandomWordFourDTO();
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);

        Set<String> translateSet = new HashSet<>();
        WordFourDTO wordFourDTO = responseWrapper.getEntity();
        translateSet.add(wordFourDTO.getTranslate1());
        translateSet.add(wordFourDTO.getTranslate2());
        translateSet.add(wordFourDTO.getTranslate3());
        translateSet.add(wordFourDTO.getTranslate4());
        Assert.assertTrue(translateSet.size() == 4);
        Mockito.verify(wordRepository,Mockito.times(7)).getRandomWord();
    }

    @Test
    public void getRandomWordTest() {
        ResponseWrapper<Word> responseWrapper = null;
        Mockito.when(wordRepository.getRandomWord()).thenReturn(new Word("one","один"));
        Mockito.when(wordRepository.count()).thenAnswer(new Answer<Long>() {
            @Override
            public Long answer(InvocationOnMock invocation) throws Throwable {
                return count;
            }
        });

        responseWrapper = serviceWord.getRandomWord();
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NO_CONTENT);

        count = 1L;
        responseWrapper = serviceWord.getRandomWord();
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
        Word word = responseWrapper.getEntity();
        Assert.assertTrue(word.getName().equals("one"));
    }

    @Test
    public void getWordById() {
        Mockito.when(wordRepository.findById(1L)).thenReturn(Optional.of(new Word("one","один")));
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
        Mockito.when(wordRepository.findByName("one")).thenReturn(Optional.of(new Word("one","один")));
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
        Word wordOne = new Word("one","один");
        Word wordTwo = new Word("two","два");
        Mockito.when(wordRepository.findByName("one")).thenReturn(Optional.of(wordOne));
        Mockito.when(wordRepository.findByName("two")).thenReturn(Optional.empty());
        Mockito.when(wordRepository.save(wordTwo)).thenAnswer(new Answer<Word>() {
            @Override
            public Word answer(InvocationOnMock invocation) throws Throwable {
                Word result = new Word("two","два");
                result.setId(22L);
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
