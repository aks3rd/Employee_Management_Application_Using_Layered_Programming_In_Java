create table employee
(
code int primary key auto_increment,
name char(50) not null,
gender char(6) not null,
panNumber char(20) not null unique,
salary numeric
)Engine=InnoDB;
