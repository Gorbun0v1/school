CREATE TABLE hogwarts.public.human
(
    id      serial primary key,
    name    varchar(15) NOT NULL,
    age     int NOT NULL,
    car_ID  BIGINT REFERENCES car(id) NOT NULL

);

CREATE TABLE hogwarts.public.car
(
    id    serial primary key,
    brand varchar(15),
    model varchar(15),
    cost  INT CHECK ( price > 0 ) NOT NULL

);

CREATE TABLE hogwarts.public.registration
(
    id       serial primary key,
    car_id   integer not null references car,
    human_id integer not null references human,
    unique (car_id, human_id)
);
INSERT INTO hogwarts.public.car(brand, model, cost)
VALUES ('Lada', 'Vesta', 120000)

INSERT INTO hogwarts.public.human(name, age, car_ID)
VALUES ('Вася', '24', 1)
VALUES ('Иван', '25', 1)


-- select student.name, student.age, faculty.name
-- from student
--          inner join faculty on faculty.id = student.faculty_id;
--
--
-- select student.name, student.age, avatar.id, avatar.media_type
-- from student
--          inner join avatar on student.id = avatar.student_id