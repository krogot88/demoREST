-- must exist database: jpatest_psql

-- create table
CREATE TABLE words
(
    id        SERIAL PRIMARY KEY NOT NULL,
    name      VARCHAR(255) NOT NULL UNIQUE,
    translate VARCHAR(255) NULL
);


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