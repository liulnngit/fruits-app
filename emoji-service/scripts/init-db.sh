#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

    CREATE SEQUENCE emoji_id_seq;

    CREATE TABLE public.emoji (
    	id SERIAL PRIMARY KEY NOT NULL,
    	name varchar(255) NOT NULL,
    	emoji varchar(255) NOT NULL
    );

    insert into public.emoji(name, emoji) values('Banana', '🍌');
    insert into public.emoji(name, emoji) values('Apple', '🍎');
    insert into public.emoji(name, emoji) values('GreenApple', '🍏');
    insert into public.emoji(name, emoji) values('Grapes', '🍇');
    insert into public.emoji(name, emoji) values('Kiwi', '🥝');
    insert into public.emoji(name, emoji) values('Lemon', '🍋');
    insert into public.emoji(name, emoji) values('Mango', '🥭');
    insert into public.emoji(name, emoji) values('Melon', '🍈');
    insert into public.emoji(name, emoji) values('Pear', '🍐');
    insert into public.emoji(name, emoji) values('Pineapple', '🍍');
    insert into public.emoji(name, emoji) values('Strawberry', '🍓');
    insert into public.emoji(name, emoji) values('Tomato', '🍅');
    insert into public.emoji(name, emoji) values('Watermelon', '🍉');


EOSQL