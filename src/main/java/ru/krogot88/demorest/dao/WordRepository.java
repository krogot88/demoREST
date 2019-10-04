package ru.krogot88.demorest.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.krogot88.demorest.model.Word;

import java.util.List;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:46
 */
public interface WordRepository extends CrudRepository<Word,Long> {
    List<Word> findAll();

    @Query(value = "SELECT * FROM words ORDER BY RANDOM() limit 1", nativeQuery = true )
    Word findNextWord();
}
