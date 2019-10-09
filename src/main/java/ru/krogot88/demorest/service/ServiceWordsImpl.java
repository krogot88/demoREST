package ru.krogot88.demorest.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;

import javax.validation.ConstraintViolationException;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
@Service
public class ServiceWordsImpl implements ServiceWord {

    @Autowired
    private WordRepository wordRepository;

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public Word getNextWord() {
        Word result = null;
        result = wordRepository.findNextWord();
        return result;
    }

    @Override
    public Word saveNewWord(Word word) {
        Word toSave = word;
        word.setId(null);  //  we get id from db)))
        try {
            return wordRepository.save(toSave);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public Word getWordById(long id) {
        return wordRepository.findById(id).orElse(null);
    }

    @Override
    public Word getWordByName(String name) {
        return wordRepository.findByName(name);
    }

    @Override
    public Word getWord(String idOrName) {
        Long id = 0L;
        try {
            id = Long.parseLong(idOrName);
            return getWordById(id);
        } catch (NumberFormatException e) {
            return getWordByName(idOrName);
        }
    }

    @Override
    public Word putWord(Word word) { //  this method don't create new one. Need fix

        //  never true  because the same code exist in REST controller in method PUT /name/{id}
        //  it is lay here for future refactor
        Word alreadyExistOnOverId = wordRepository.findByName(word.getName());
        if(alreadyExistOnOverId.getId() != word.getId())
            return null;

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long id = word.getId();
        Word temp = wordRepository.findById(id).orElse(null);

        if(temp != null) {  // ... and update existing word
            temp.setName(word.getName());
            temp.setTranslate(word.getTranslate());
            session.update(temp);
            transaction.commit();  //  where throw exception if we try fetch word with Name that already exist
            session.close();
            return temp;
        } else {  // ... if not found - return null
            //
            //  need POST  word to specified id. But now postgre increase id by next value
            //  so now  just return null - " 404 not found"
            //
            return null;
        }
    }

    @Override
    public Word putWordOrUpdate(Word word) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Word temp = wordRepository.findByName(word.getName());

        if(temp != null) {
            temp.setTranslate(word.getTranslate());
            session.update(temp);
            transaction.commit();
            session.close();
        } else  {
            temp = wordRepository.save(word);
        }
        return temp;
    }

    @Override
    public boolean deleteWord(String idOrName) {
        Long id = 0L;
        try {
            id = Long.parseLong(idOrName);
            Word forDelete = wordRepository.findById(id).orElse(null);
            if(forDelete == null)
                return false;
            else {
                wordRepository.deleteById(id);
                return true;
            }
        } catch (NumberFormatException e) {
            Word forDelete = wordRepository.findByName(idOrName);
            if(forDelete == null)
                return false;
            else {
                wordRepository.delete(forDelete);
                return true;
            }
        }
    }

    @Override
    public Page<Word> getPaginatedWords(Pageable pageable) {
        return wordRepository.findAll(pageable);
    }
}
