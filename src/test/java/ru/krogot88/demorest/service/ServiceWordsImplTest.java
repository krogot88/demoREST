package ru.krogot88.demorest.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.dto.ResponseWrapper;
import ru.krogot88.demorest.model.Word;

import java.util.Optional;

/**
 * User: Сашок  Date: 19.10.2019 Time: 15:50
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceWordsImplTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private ServiceWord serviceWord = new ServiceWordsImpl();

    @Before
    public void init(){
        Mockito.when(wordRepository.findById(1L)).thenReturn(Optional.of(new Word("see","видеть")));
    }

    @Test
    public void getWordById() {
        Assert.assertEquals(serviceWord.getWordById(1L).getEntity().getName(),"see");
        Mockito.verify(wordRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

}
