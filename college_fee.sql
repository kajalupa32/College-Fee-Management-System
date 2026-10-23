CREATE DATABASE IF NOT EXISTS college_fee;
USE college_fee;

CREATE TABLE student (
  student_id INT AUTO_INCREMENT PRIMARY KEY,
  prn        VARCHAR(20) NOT NULL UNIQUE,
  name       VARCHAR(100) NOT NULL,
  course     VARCHAR(50) NOT NULL,
  year_no    INT NOT NULL,
  phone      VARCHAR(15),
  email      VARCHAR(100)
);

CREATE TABLE fee_structure (
  fee_id    INT AUTO_INCREMENT PRIMARY KEY,
  course    VARCHAR(50) NOT NULL,
  year_no   INT NOT NULL,
  total_fee DECIMAL(10,2) NOT NULL,
  UNIQUE (course, year_no)
);

CREATE TABLE receipt (
  receipt_id   INT AUTO_INCREMENT PRIMARY KEY,
  student_id   INT NOT NULL,
  receipt_date DATE NOT NULL,
  amount       DECIMAL(10,2) NOT NULL,
  mode         VARCHAR(20) NOT NULL,
  FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE
);

-- sample data
INSERT INTO fee_structure(course,year_no,total_fee) VALUES ('BCA',1,45000),('BCA',2,48000),('BBA',1,40000);
INSERT INTO student(prn,name,course,year_no,phone,email)
VALUES ('PRN001','Amit Sharma','BCA',1,'9999999991','amit@mail.com'),
       ('PRN002','Neha Patil','BBA',1,'9999999992','neha@mail.com');
INSERT INTO receipt(student_id,receipt_date,amount,mode) VALUES (1,CURDATE(),20000,'Cash');