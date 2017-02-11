﻿/*
create database jpatest;

use jpatest;

CREATE TABLE STUDENT  (
	ID CHAR(100) NOT NULL PRIMARY KEY,
    ROLLNUMBER INT,
	NAME VARCHAR(200),
    FIRSTNAME VARCHAR(200)
) ENGINE=InnoDB;

insert into STUDENT (id, rollnumber, name, firstname) values ('1', 1, 'Dantito', 'Ricalde Rivera');

insert into STUDENT (id, rollnumber, name, firstname) values ('2', 2, 'Daniel Nicolás', 'Ricalde Rivera');
*/

select count(*) from student;

select * from student where id = '4bcb45fc-8681-459b-a401-42aee8a416a8'

select * from Student;

select max(s.rollnumber) from Student s;

update Student set rollnumber = RAND() * 1000;
