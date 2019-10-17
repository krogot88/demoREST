package ru.krogot88.demorest.dto;

import org.springframework.http.HttpStatus;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 14.10.2019 Time: 22:45
 */
public class WordBox {

    private Word word;
    private HttpStatus httpStatus;

    public WordBox() {
    }

    public WordBox(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public WordBox(Word word, HttpStatus httpStatus) {
        this.word = word;
        this.httpStatus = httpStatus;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
