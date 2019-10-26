CREATE TABLE person_progress
(
    person_id    BIGINT NOT NULL,
    word_id      BIGINT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons(id),
    FOREIGN KEY (word_id) REFERENCES words(id)
);