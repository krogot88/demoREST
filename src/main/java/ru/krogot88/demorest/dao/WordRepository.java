package ru.krogot88.demorest.dao;

import org.springframework.data.repository.CrudRepository;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:46
 */
public interface WordRepository extends CrudRepository<Word,Long> {
}
