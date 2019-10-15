package ru.krogot88.demorest.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.krogot88.demorest.model.Word;

import java.util.List;
import java.util.Optional;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:46
 */
public interface WordRepository extends CrudRepository<Word,Long> {
    List<Word> findAll();

    Optional<Word> findByName(String name);

    @Query(value = "SELECT * FROM words ORDER BY RANDOM() limit 1", nativeQuery = true )
    Word getRandomWord();

    Page<Word> findAll(Pageable pageable);
}
