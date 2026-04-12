# MySQL - Linux Mint 20

Remove previous version 
```
$ sudo apt-get remove --purge mysql*  
$ sudo apt-get purge mysql*  
$ sudo apt-get autoremove  
$ sudo apt-get autoclean  
$ sudo apt-get remove dbconfig-mysql
```

Check linux version  

`$ lsb_release -a`  


Install mysql

`$ sudo apt-get update`  
`$ sudo apt-get install mysql-server`

Check status
`$ sudo systemctl status mysql`

Assign root user password

```bash
$ sudo mysql -u root
mysql> SELECT user,host,authentication_string,plugin FROM mysql.user WHERE user='root';
mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
mysql> FLUSH PRIVILEGES;
mysql> quit
```  
---
```
mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
ERROR 1819 (HY000): Your password does not satisfy the current policy requirements

-- Source - https://stackoverflow.com/a/43094873
-- Posted by Hung Nguyen, modified by community. See post 'Timeline' for change history
-- Retrieved 2026-04-11, License - CC BY-SA 4.0

ysql> SHOW VARIABLES LIKE 'validate_password%';
+-------------------------------------------------+--------+
| Variable_name                                   | Value  |
+-------------------------------------------------+--------+
| validate_password.changed_characters_percentage | 0      |
| validate_password.check_user_name               | ON     |
| validate_password.dictionary_file               |        |
| validate_password.length                        | 8      |
| validate_password.mixed_case_count              | 1      |
| validate_password.number_count                  | 1      |
| validate_password.policy                        | MEDIUM |
| validate_password.special_char_count            | 1      |
+-------------------------------------------------+--------+
8 rows in set (0,02 sec)

mysql> SET GLOBAL validate_password.length = 5;
Query OK, 0 rows affected (0,00 sec)

mysql> SET GLOBAL validate_password.number_count = 0;
Query OK, 0 rows affected (0,00 sec)

mysql> SET GLOBAL validate_password.policy = 0;
Query OK, 0 rows affected (0,00 sec)

mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
Query OK, 0 rows affected (0,01 sec)


```
---


Login to mysql 
`$ mysql -u root -p`  

```bash
mysql> show databases;
mysql> create database sample;

```


---
[Download sample database](https://github.com/datacharmer/test_db/tree/master)  

```
$ git clone https://github.com/datacharmer/test_db.git
$ cd test_db
$ mysql -u root -p < employees.sql`  
$ mysql -u root -p
mysql> show databases;
mysql> use employees;;
mysql> describe employees;
mysql> show tables;
+----------------------+
| Tables_in_employees  |
+----------------------+
| current_dept_emp     |
| departments          |
| dept_emp             |
| dept_emp_latest_date |
| dept_manager         |
| employees            |
| salaries             |
| titles               |
+----------------------+
8 rows in set (0,01 sec)
mysql> describe employees;
+------------+---------------+------+-----+---------+-------+
| Field      | Type          | Null | Key | Default | Extra |
+------------+---------------+------+-----+---------+-------+
| emp_no     | int           | NO   | PRI | NULL    |       |
| birth_date | date          | NO   |     | NULL    |       |
| first_name | varchar(14)   | NO   |     | NULL    |       |
| last_name  | varchar(16)   | NO   |     | NULL    |       |
| gender     | enum('M','F') | NO   |     | NULL    |       |
| hire_date  | date          | NO   |     | NULL    |       |
+------------+---------------+------+-----+---------+-------+
```


# MySQL Workbench

https://dev.mysql.com/downloads/workbench/
Download image from mysql.com
```
sudo dpkg -i mysql-workbench-community_8.0.44-1ubuntu24.04_amd64.deb
sudo apt install libmysqlclient21
sudo apt --fix-broken install
```
Run first install command (dpkg -i...) again. It should install now.
```
sudo dpkg -i mysql-workbench-community_8.0.44-1ubuntu24.04_amd64.deb
```

---

# Spring Boot 
pom.xml :  
```
<!-- MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```


application.properties :  
```
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```