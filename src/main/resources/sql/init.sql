-- must exist database: jpatest_psql

-- create table persons
CREATE TABLE persons
(
    id       SERIAL PRIMARY KEY NOT NULL,
    login    VARCHAR(255)       NOT NULL UNIQUE,
    password VARCHAR(255)       NOT NULL
);

-- create table roles
CREATE TABLE roles
(
    id        SERIAL PRIMARY KEY NOT NULL,
    rolename  VARCHAR(255)       NOT NULL UNIQUE
);

-- create table person_roles
CREATE TABLE person_roles
(
    person_id    BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- create table words
CREATE TABLE words
(
    id        SERIAL PRIMARY KEY NOT NULL,
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
INSERT INTO public.words(name, translate) VALUES ('see','видеть');
INSERT INTO public.words(name, translate) VALUES ('look','смотреть');
INSERT INTO public.words(name, translate) VALUES ('know','знать');
INSERT INTO public.words(name, translate) VALUES ('open','открыть');
INSERT INTO public.words(name, translate) VALUES ('seem','казаться');
INSERT INTO public.words(name, translate) VALUES ('together','вместе');
INSERT INTO public.words(name, translate) VALUES ('white','белый');
INSERT INTO public.words(name, translate) VALUES ('children','дети');
INSERT INTO public.words(name, translate) VALUES ('begin','начинать');
INSERT INTO public.words(name, translate) VALUES ('letter','письмо');
INSERT INTO public.words(name, translate) VALUES ('until','до тех пор');
INSERT INTO public.words(name, translate) VALUES ('river','река');
INSERT INTO public.words(name, translate) VALUES ('enough','достаточно');
INSERT INTO public.words(name, translate) VALUES ('ready','готовый');
INSERT INTO public.words(name, translate) VALUES ('feel','чувствовать');
INSERT INTO public.words(name, translate) VALUES ('talk','говорить');
INSERT INTO public.words(name, translate) VALUES ('bird','птица');
INSERT INTO public.words(name, translate) VALUES ('song','песня');
INSERT INTO public.words(name, translate) VALUES ('measure','мера');
INSERT INTO public.words(name, translate) VALUES ('product','продукт');
INSERT INTO public.words(name, translate) VALUES ('black','черный');
INSERT INTO public.words(name, translate) VALUES ('already','уже');
INSERT INTO public.words(name, translate) VALUES ('green','зеленый');
INSERT INTO public.words(name, translate) VALUES ('brown','коричневый');
INSERT INTO public.words(name, translate) VALUES ('mother','мама');
INSERT INTO public.words(name, translate) VALUES ('father','папа');
INSERT INTO public.words(name, translate) VALUES ('sister','сестра');
INSERT INTO public.words(name, translate) VALUES ('brother','брат');
INSERT INTO public.words(name, translate) VALUES ('shark','акула');
INSERT INTO public.words(name, translate) VALUES ('dollar','доллар');
INSERT INTO public.words(name, translate) VALUES ('nine','девять');