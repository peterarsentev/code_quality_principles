--Есть две таблицы в базе.

CREATE TABLE girl (
  id serial PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE toy (
  id serial PRIMARY KEY,
  name VARCHAR
);

--1. Написать sql инструкцию. Инструкция должна добавить колонку toy_id в таблицу girl.

--2. Написать sql запрос на получение имени девочки и ее игрушки.

--3. Написать sql запрос на получение игрушек не привязанных к девочкам.