-- create table persons
CREATE TABLE persons
(
    id       INT auto_increment PRIMARY KEY NOT NULL ,
    login    VARCHAR(255)       NOT NULL UNIQUE,
    password VARCHAR(255)       NOT NULL
);

-- create table roles
CREATE TABLE roles
(
    id        INT auto_increment PRIMARY KEY NOT NULL,
    rolename  VARCHAR(255)       NOT NULL UNIQUE
);

-- create table person_roles
CREATE TABLE person_roles
(
    person_id    INT NOT NULL,
    role_id      INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- create table words
CREATE TABLE words
(
    id        INT auto_increment PRIMARY KEY NOT NULL,
    name      VARCHAR(255)       NOT NULL UNIQUE,
    translate VARCHAR(255)       NULL
);

INSERT INTO  public.persons(login, password) VALUES ('admin','$2a$10$miBQoc0AY707mmunbCi3XOXDnomPFwhwjN7utR438V0APQ2VIB7pC');
INSERT INTO  public.persons(login, password) VALUES ('user','$2a$10$qt2wg9djI5kzq/Wszr5M1uP.dYAVmU9xNaXbMY.ztPCc0rezqWL.m');

INSERT INTO roles(roleName) VALUES ('ROLE_ADMIN');
INSERT INTO roles(roleName) VALUES ('ROLE_USER');

INSERT INTO person_roles(person_id, role_id) VALUES (1,1);
INSERT INTO person_roles(person_id, role_id) VALUES (1,2);
INSERT INTO person_roles(person_id, role_id) VALUES (2,2);

-- inserting data
INSERT INTO public.words(name, translate) VALUES ('one','один');
INSERT INTO public.words(name, translate) VALUES ('two','два');
INSERT INTO public.words(name, translate) VALUES ('three','три');
