package ru.krogot88.demorest.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.dto.ResponseWrapper;
import ru.krogot88.demorest.dto.WordFourDTO;
import ru.krogot88.demorest.model.Word;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * User: Сашок  Date: 19.10.2019 Time: 15:50
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceWordsImplTest {

    private DataBaseMock dataBaseMock;

    private class DataBaseMock {
        private Map<Long,Word> map = new HashMap<>();
        private Long nextKey;

        {
            map.put(1L,new Word("one","один"));
            map.put(2L,new Word("two","два"));
            map.put(3L,new Word("three","три"));
            nextKey = 4L;
        }

        public Word getEntity(Long id) {
            return map.get(id);
        }

        public Long count() {
            return new Long(nextKey - 1);
        }

        public Word saveEntity(Word word) {
            map.put(nextKey,word);
            nextKey++;
            return word;
        }

        public Word getRandomWord() {
            Long random = ThreadLocalRandom.current().nextLong(nextKey + 1L);
            return map.get(random);
        }

    }

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private ServiceWord serviceWord = new ServiceWordsImpl();

    @Before
    public void init(){
        dataBaseMock = new DataBaseMock();
        Mockito.when(wordRepository.findById(1L)).thenReturn(Optional.of(dataBaseMock.getEntity(1L)));
        Mockito.when(wordRepository.count()).thenReturn(dataBaseMock.count());
        Mockito.when(wordRepository.save(new Word("four","четыре"))).thenReturn(dataBaseMock.saveEntity(new Word("four","четыре")));
        Mockito.when(wordRepository.getRandomWord()).thenReturn(dataBaseMock.getRandomWord());
    }

    @Test
    public void getRandomWordFourDTOTest() {
        ResponseWrapper<WordFourDTO> responseWrapper = null;
        responseWrapper = serviceWord.getRandomWordFourDTO();
        Assert.assertTrue(responseWrapper.getEntity() == null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.NO_CONTENT);

        wordRepository.save(new Word("four","четыре"));
        responseWrapper = serviceWord.getRandomWordFourDTO();
        Assert.assertTrue(responseWrapper.getEntity() != null);
        Assert.assertTrue(responseWrapper.getHttpStatus() == HttpStatus.OK);
    }

    @Test
    public void getWordById() {
        Assert.assertEquals(serviceWord.getWordById(1L).getEntity().getName(),"one");
        Mockito.verify(wordRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

}
