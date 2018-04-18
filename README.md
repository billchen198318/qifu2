# qifu2
bambooBSC-8 core system.

bambooBSC-8 core system. Backend Admin WEB base on JAVA & Spring / Spring MVC & hibernate - MyBatis & Bootstrap v4(vali-admin) & Jasperreport, WebService apahe cxf, Activiti BPM, MQ 

Database table doc:
https://github.com/billchen198318/qifu2/blob/master/doc/qifu2-table-doc.odt

Database source script:
https://github.com/billchen198318/qifu2/blob/master/doc/qifu2.sql


<br>
<br>

### Create database

```
MYSQL> create database qifu2;
MYSQL> exit;

# mysql qifu2 -u root -p < qifu.sql

```

### Config datasource ( applicationContext-dataSource.properties )

```
dataSource.jdbcUrl=jdbc:mysql://localhost/qifu2?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
dataSource.user=MYSQL-ACCOUNT
dataSource.password=MYSQL-PASSWORD
```

<br>
<br>

### Login page:
```
http://[YOU-SERVER]:[PORT]/core-web/index.do
```

```
http://127.0.0.1:8080/core-web/index.do
```

<br>
<br>

### Administrator account: 
account: admin <br>
passowrd: admin99 <br>


<br>
<br>

### Screenshot: 

<img alt="demo1" src="https://raw.githubusercontent.com/billchen198318/qifu2/master/doc/pic/qifu2-001.png">
<img alt="demo2" src="https://raw.githubusercontent.com/billchen198318/qifu2/master/doc/pic/qifu2-002.png">


