#### ssm项目初练——图书借阅系统（BookLendingSystem）



框架：spring5+springMVC+Mybatis 3.5.2 （ssm）框架

开发环境：win10+IDEA2019+mysql 8.0+Tomcat9+maven3.6



项目设计思路：

需求分析->设计数据库->业务->前端界面



##### 第一步：数据库建表



```sql
CREATE DATABASE `BookLendingSystem_ssm`; 
USE `BookLendingSystem_ssm`; 

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` ( 
    `bookID` INT(10) NOT NULL AUTO_INCREMENT COMMENT '图书id',
    `bookName` VARCHAR(100) NOT NULL COMMENT '书名',
    `number` INT(11) NOT NULL COMMENT '数量',
    `detail` VARCHAR(200) NOT NULL COMMENT '简介',
    KEY `bookID` (`bookID`) 
)ENGINE=INNODB DEFAULT CHARSET=utf8 

INSERT INTO `books` (`bookID`, `bookName`, `number`,`detail`) VALUES
(01, 'Java',10, 'Java,从入门到放弃'), 
(02, 'Mysql',12,'轻量级关系型数据库'), 
(03, 'Linux',6,'从入门到入土'), 
(04, 'C语言程序设计',20, '从前有人说过：c语言才是最美妙的！'), 
(05, '数据结构',43,'数据结构与算法不得不说的故事'), 
(06, '恋爱手册',3,'单身狗必备的一百本书之一'); 

CREATE TABLE `lending` ( 
    `bookID` INT(10) NOT NULL COMMENT '图书ID', 
    `student_id` INT(10) NOT NULL COMMENT '学号', 
    `lending_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间' , 
    PRIMARY KEY (`bookID`, `student_id`), 
    INDEX `idx_lending_time` (`lending_time`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='预约图书表'; 

CREATE TABLE `student`( 
    `student_id` BIGINT(20) NOT NULL COMMENT '学生ID', 
    `password` BIGINT(20) NOT NULL COMMENT '密码', 
    PRIMARY KEY(`student_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='学生统计表'; 

INSERT INTO `student`(`student_id`,`password`) VALUES 
(1711239141,111111),
(1211210102,436457), 
(1811200103,646541), 
(1911240104,789740), 
(1911203105,220440), 
(1711202106,811111), 
(1711200107,233333);


```



##### 第二步：根据预计实现功能的需要导入maven依赖，并新建各自配置文件

```xml
    <dependencies>
        <!-- 单元测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

        <!-- 1.日志 -->
        <!-- 实现slf4j接口并整合 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.1.1</version>
        </dependency>

        <!-- 2.数据库 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>

        </dependency>
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>


        <!-- DAO: MyBatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>

        <!-- 3.Servlet web -->
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!--Servlet - JSP -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.9</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.9.1</version>
            <exclusions>
                <exclusion>
                    <groupId>com.fasterxml.jackson.core</groupId>
                    <artifactId>jackson-annotations</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.9.9</version>
        </dependency>



        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
        </dependency>

        <!-- 4.Spring -->
        <!-- 1)Spring核心 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!-- 2)Spring DAO层 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!-- 3)Spring web -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!-- 4)Spring test -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>

        <!-- redis客户端:Jedis -->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.7.3</version>
        </dependency>
        <dependency>
            <groupId>com.dyuproject.protostuff</groupId>
            <artifactId>protostuff-core</artifactId>
            <version>1.0.8</version>
        </dependency>
        <dependency>
            <groupId>com.dyuproject.protostuff</groupId>
            <artifactId>protostuff-runtime</artifactId>
            <version>1.0.8</version>
        </dependency>

        <!-- Map工具类 -->
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2</version>
        </dependency>

        <!--lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.10</version>
        </dependency>
    </dependencies>
```

还有静态资源导出问题

```xml
<build>
   <resources>
       <resource>
           <directory>src/main/java</directory>
           <includes>
               <include>**/*.properties</include>
               <include>**/*.xml</include>
           </includes>
           <filtering>false</filtering>
       </resource>
       <resource>
           <directory>src/main/resources</directory>
           <includes>
               <include>**/*.properties</include>
               <include>**/*.xml</include>
           </includes>
           <filtering>false</filtering>
       </resource>
   </resources>
</build>
```



新建配置文件mybatis-config.xml:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!-- 使用jdbc的getGeneratedKeys获取数据库自增主键值 -->
        <setting name="useGeneratedKeys" value="true" />
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <setting name="useColumnLabel" value="true" />
     <!--使用驼峰命名转换，可以自动去掉数据库内字段名的下划线并转为java驼峰格式，例如bookName -->
        <setting name="mapUnderscoreToCamelCase" value="true" />

    </settings>
    <!--取别名 -->
    <typeAliases>
        <package name="com.wang.book.pojo"/>
    </typeAliases>



</configuration>
```



新建applicationContext.xml作为spring部分的总配置，可以在里面引用mvc，service各层的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

   

</beans>
```



然后配置数据库jdbc相关文件：jdbc.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver

jdbc.url=jdbc:mysql://localhost:3306/booklendingsystem_ssm?useSSL=true&useUnicode=true&characterEncoding=UTF-8
jdbc.username=你的数据库用户名
jdbc.password=你的数据库密码
```





##### 第三步：创建实体类并完善配置文件

pojo，dao



新建路径（包）

com.wang.book.controller，dao和pojo



**pojo**

PS：注意实体类中我使用了lombok注解来偷懒（可以百度了解下lombok注解，懒人必备hh

*实体类*

com.wang.book.pojo.books 负责books表数据对象封装

```java
package com.wang.book.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books {

    private int bookId;//图书ID
    private String bookName;//图书名称
    private int number;//馆藏数量
    private String detail;//图书简介



}
```

还有lending表（借阅表）的对应数据封装：

```java
package com.wang.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class lending {

    private int bookId;
    private int studentId;
    private Date lendingTime;
    private Books books;
}
```

以及Student学生类，是打算用来做验证用的

```java
package com.wang.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long studentId;
    private Long password;
}
```



**dao**

com.wang.book.dao 负责与数据库的交互，而交互所组成的各种业务放在service层内

*定义接口和对应的抽象方法：*

```java
package com.wang.book.dao;

import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

    /* === 图书相关 ===*/
   //根据id查询书
    Books queryById(@Param("bookId") int id);
    List<Books> queryByName(@Param("bookName") String name);
    List<Books> queryAll(@Param("offset") int offset, @Param("limit") int limit);
    //减少管存数量,用返回值可判断当前库存是否还有书
    int reduceNumber(int bookId);


}


```

注意：@Param("bookID")可以方便给sql语句传入参数

当你使用了@Param注解来声明参数时，使用 #{} 或 ${} 的方式都可以,当你不使用@Param注解来声明参数时，必须使用使用 #{}方式。如果使用 ${} 的方式，会报错

关于sqls中的\#{}和${}

\#{}表示一个占位符号，通过#{}可以实现preparedStatement向占位符中设置值，自动进行java类型和jdbc类型转换，**#{}可以有效防止sql注入**。 #{}可以接收简单类型值或pojo属性值。 如果parameterType传输单个简单类型值，#{}括号中可以是value或其它名称。

**关于模糊查询和预防sql注入**

select *from mybatis.user where name like "%"#{value}"%"

配置中书写如下

```xml
<select id="queryByName" parameterType="Books" resultType="com.wang.book.pojo.Books">
    SELECT bookID,bookName,number,detail FROM books

        where bookName like "%"#{bookName}"%"

</select>
```

换成具体sql语句差不多就是下面这个意思

```sql
SELECT bookID,bookName,number,detail FROM books

where bookName like '%恋%'
```





**定义接口对应配置**

*然后定义接口的相关mapper配置：BookMapper.xml*

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.wang.book.dao.BookMapper">



    <select id="queryById" resultType="com.wang.book.pojo.Books" >

        SELECT *  FROM booklendingsystem_ssm.books
        WHERE bookID = #{bookId}
    </select>

    <select id="queryByName" parameterType="Books" resultType="com.wang.book.pojo.Books">

        SELECT bookID,bookName,number,detail FROM books

            where bookName like "%"#{bookName}"%"

    </select>

    <select id="queryAll" resultType="com.wang.book.pojo.Books">
		SELECT bookID,bookName,number,detail
		FROM booklendingsystem_ssm.books
		ORDER BY bookID
		LIMIT #{offset}, #{limit}

	</select>

    <update id="reduceNumber">
		UPDATE books
		SET number = number - 1
		WHERE
			bookID = #{bookId}
		AND number > 0
	</update>


</mapper>
```

**关于LIMIT 和 OFFSET 子句**

分页（分段，

LIMIT 和 OFFSET 子句通常和ORDER BY 语句一起使用，当我们对整个结果集排序之后，我们可以 LIMIT 来指定要返回多少行结果 , 用 OFFSET来指定从哪一行开始返回。（如果offset 3，就是从第四行开始）

意思就是说，LIMIT决定数量，OFFSET决定起点

**还有升序排列，**

```sql
ORDER BY bookID ASC
```



同理，编写LendingMapper.java和LendingMapper.xml，以及StudentMapper的

```java
package com.wang.book.dao;

import com.wang.book.pojo.Lending;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LendingMapper {


    /* === 借阅相关 ===*/
    //通过图书ID和学生ID预约书籍，并插入
    int insertLending(@Param("bookId") int bookId, @Param("studentId") int studentId);
    //通过学生ID可以查询已预约的书
    List<Lending> queryLending(int studentId);

}

```

LendingMapper接口对应mapper文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wang.book.dao.LendingMapper">

    <insert id="insertLending">
        <!-- ignore 主键冲突，报错 -->
        INSERT ignore INTO lending (bookID, student_id)
        VALUES (#{bookId}, #{studentId})
    </insert>

    <!--查询某学生的预约记录 -->
    <select id="queryLending"  resultType="com.wang.book.pojo.Lending">

        SELECT a.bookId,a.student_id,a.lending_time,
        b.bookID "books.bookID",b.`bookName` "books.bookName",
        b.number "books.number",
        b.detail "books.detail"

        FROM
        lending a  <!--表也可以起别名来偷懒hh -->
        INNER JOIN books b ON a.bookID = b.bookID
        WHERE
        a.student_id = #{studentId}
    </select>

</mapper>
```

然后是StudentMapper接口：

```java
package com.wang.book.dao;

import com.wang.book.pojo.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {


    /* === 学生相关 ===*/
    //向数据库验证输入的密码是否正确

    Student queryStudent(@Param("studentId") int studentId, @Param("password") int password);

}

```

以及接口对应配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wang.book.dao.StudentMapper">

    <select id="queryStudent" resultType="com.wang.book.pojo.Student">
		SELECT
			student_id,
			password
		FROM
			student
		WHERE
			student_id=#{studentId}
		AND password=#{password}
	</select>

</mapper>
```

最后，将这三个mapper都绑定在mybatis-config.xml中

绑定后代码如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!-- 使用jdbc的getGeneratedKeys获取数据库自增主键值 -->
        <setting name="useGeneratedKeys" value="true" />
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <setting name="useColumnLabel" value="true" />
        <!--使用驼峰命名转换，可以自动去掉数据库内字段名的下划线并转为java驼峰格式，例如bookName -->
        <setting name="mapUnderscoreToCamelCase" value="true" />

    </settings>
    <!--取别名 -->
    <typeAliases>
        <package name="com.wang.book.pojo"/>
    </typeAliases>


    <mappers>
        <mapper resource="com/wang/book/dao/BookMapper.xml"/>
        <mapper resource="com/wang/book/dao/LendingMapper.xml"/>
        <mapper resource="com/wang/book/dao/StudentMapper.xml"/>

    </mappers>


</configuration>
```

到了这步DAO层就差不多搞定了，下面是一些备注：

在借阅表中需要关联图书表，所以需要用INNER JOIN子句：

**关于INNER JOIN**

MySQL 中的`INNER JOIN`子句将一个表中的行与其他表中的行进行匹配，并允许从两个表中查询包含列的行记录，

首先，需要在FROM子句中指定主表；FORM A INNER JOIN B

然后，将要连接的表加入；

最后，需要设置连接条件，

例如：

```
<select id="querylending"  resultType="com.wang.book.pojo.Lending">

    SELECT a.bookId,a.student_id,
    b.bookID "books.bookID",
    b.`name` "books.bookName",
    b.detail "books.detail",
    b.number "books.number"
    FROM
    lending a  <!--表也可以起别名来偷懒hh -->
    INNER JOIN books b ON a.bookID = b.bookID
    WHERE
    a.student_id = #{studentId}
</select>
```

PS:并不建议关联三张表以上

INNER JOIN 关联三张数据表的写法：
SELECT * FROM (表1 INNER JOIN 表2 ON 表1.字段号=表2.字段号) INNER JOIN 表3 ON 表1.字段号=表3.字段号.



**然后就是service层**

和dao层差不多，就是利用dao层中各种sql语句实现具体的业务逻辑，代码功能越复杂业务层作用越明显（这个ssm小项目就不太明显）



**定义接口BookService.java**

```java
package com.wang.book.service;

import com.wang.book.dto.LendingState;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;

import java.util.List;

public interface BookService {


        //根据bookID查询
        Books getById(int bookId);

        //查询所有书籍
        List<Books> getList();

        //登陆时查询数据库是否有该学生记录
        Student validateStu(int studentId, int password);

        //按照图书名称查询
        List<Books> getSomeList(String name);

        // 查看某学生预约的所有图书
        List<Lending> getLendingByStu(int studentId);
        //利用学生ID来预约图书

        LendingState lendingState(int bookId, int studentId);

    }


```

还需要单独编写一个预约状态类，异常处理类：

预约状态类放在dto包（DTO（Data Transfer Object）数据传输对象）下：

预约状态类（LendingState）

```java
package com.wang.book.dto;


import com.wang.book.enums.LendingStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LendingState {


        // 图书ID
        private int bookId;

        // 预约结果状态
        private int state;

        // 状态标识
        private String stateInfo;

        public LendingState() {
        }

        // 预约失败的构造器
        public LendingState(int bookId, LendingStateEnum stateEnum) {
            this.bookId = bookId;
            this.state = stateEnum.getState();
            this.stateInfo = stateEnum.getStateInfo();
        }

        //set get 方法！
        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }

        @Override
        public String toString() {
            return "AppointExecution [bookId=" + bookId + ", state=" + state + ", stateInfo=" + stateInfo+"]";
        }
    }
```

三个异常类：预约异常，库存不足异常，重复预约异常

```java
package com.wang.book.Exception;

public class LendingException extends RuntimeException{
    public LendingException(String message) {
        super(message);
    }

    public LendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
```



```java
package com.wang.book.Exception;

public class NoNumberException extends RuntimeException{

    //库存不足异常
        public NoNumberException(String message) {
            super(message);
        }

        public NoNumberException(String message, Throwable cause) {
            super(message, cause);
        }

    }
```



```java
package com.wang.book.Exception;

public class RepeatLendingException extends RuntimeException{

    //重复预约异常
        public RepeatLendingException(String message) {
            super(message);
        }

        public RepeatLendingException(String message, Throwable cause) {
            super(message, cause);
        }
}
```

然后就是写接口的实现类：BookServiceImpl.java

```java
package com.wang.book.service;

import java.util.List;

import com.wang.book.Exception.LendingException;
import com.wang.book.Exception.NoNumberException;
import com.wang.book.Exception.RepeatLendingException;
import com.wang.book.dao.BookMapper;
import com.wang.book.dao.LendingMapper;
import com.wang.book.dao.StudentMapper;
import com.wang.book.dto.LendingState;
import com.wang.book.enums.LendingStateEnum;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService{
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    //@Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 可以消除 set ，get方法。
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private LendingMapper lendingMapper;
    @Autowired
    private StudentMapper studentMapper;


    /*public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public void setLendingMapper(LendingMapper lendingMapper) {
        this.lendingMapper = lendingMapper;
    }
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }*/


    @Override
    public Books getById(int bookId) {
        return bookMapper.queryById(bookId);
    }

    @Override
    public List<Books> getList() {
        return bookMapper.queryAll(0, 1000);
    }

    @Override
    public Student validateStu(int studentId,int password){
        return studentMapper.queryStudent(studentId, password);
    }
    @Override
    public List<Books> getSomeList(String name) {

        return bookMapper.queryByName(name);
    }


    @Override
    public List<Lending> getLendingByStu(int studentId) {
        return lendingMapper.queryLending(studentId);

    }
    @Override
    @Transactional
    public LendingState lendingState(int bookId, int studentId) {//在Dao的基础上组织逻辑，形成与web成交互用的方法
        try{													  //返回成功预约的类型。
            int update=bookMapper.reduceNumber(bookId);//减库存
            if(update<=0){//已经无库存！
                throw new NoNumberException("no number");
            }else{
                //执行预约操作
                int insert=lendingMapper.insertLending(bookId, studentId);
                if(insert<=0){//重复预约
                    throw new RepeatLendingException("不要重复预约");
                }else{//预约成功
                    return new LendingState(bookId, LendingStateEnum.SUCCESS);
                }

            }
        } catch (NoNumberException e1) {
            throw e1;
        } catch (RepeatLendingException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            throw new LendingException("Lending inner error:" + e.getMessage());
        }
    }
}
```



**然后开始整合spring层**



- 进行spring与mybatis整合:

  **spring-dao.xml**，里面有关联数据库配置文件，连接池，sqlSessionFactory等

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 配置整合mybatis过程 -->
    <!-- 1.配置数据库相关参数   properties的属性：${url} -->
    <context:property-placeholder location="classpath:jdbc.properties" />

    <!-- 2.数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 配置连接池属性 -->
        <property name="driverClass" value="${jdbc.driver}" />
        <property name="jdbcUrl" value="${jdbc.url}" />
        <property name="user" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />

        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30" />
        <property name="minPoolSize" value="10" />
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false" />
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000" />
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2" />
    </bean>
    <!-- 3.配置SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 配置MyBaties全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis-config.xml" />

        <!-- 扫描pojo包 使用别名 -->
        <property name="typeAliasesPackage" value="com.wang.book.pojo" />

    </bean>

    <!-- 4.配置扫描Dao接口包，动态实现Dao接口，注入到spring容器中 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="com.wang.book.dao"/>
    </bean>

</beans>
```

**然后开始配置spring-service.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx.xsd">
    <!-- 扫描service包下所有使用注解的类型 -->
    <!-- 扫描service相关的bean -->
    <context:component-scan base-package="com.wang.book.service"/>

    <!--BookServiceImpl注入到IOC容器中-->
<!--
    <bean id="BookServiceImpl" class="com.wang.book.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>

    <bean id="BookServiceImpl" class="com.wang.book.service.BookServiceImpl">
        <property name="lendingMapper" ref="lendingMapper"/>
    </bean>
    <bean id="BookServiceImpl" class="com.wang.book.service.BookServiceImpl">
        <property name="studentMapper" ref="studentMapper"/>
    </bean>
-->





    <!-- 配置事务管理器 -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource" />
    </bean>
    <!-- 配置基于注解的声明式事务,就可以使用@Transactionl 注解可以控制事务就更通俗易懂 -->
    <tx:annotation-driven transaction-manager="transactionManager" />
</beans>
```



**最后整合springMVC层**

想要变成一个web项目，需要添加web支持：项目右键添加（add frameworksupport），然后勾选Web Application 

然后配置web.xml:

包括加载dispatcherServlet，然后添加乱码过滤，注意是/*，只有/是无法拦截页面的乱码的，然后设置session过期时间防止无响应

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">






    <!--DispatcherServlet-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>

            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--encodingFilter-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>





</web-app>


```



然后SpringMVC还需要在对应配置文件(spring-mvc.xml)中添加注解驱动，静态资源过滤

扫描包：controller，视图解析器等等：

**spring-mvc.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context.xsd
   http://www.springframework.org/schema/mvc
   https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 配置SpringMVC -->
    <!-- 1.开启SpringMVC注解驱动 -->
    <mvc:annotation-driven />
    <!-- 2.静态资源默认servlet配置-->
    <mvc:default-servlet-handler/>

    <!-- 3.配置jsp 显示ViewResolver视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
    </bean>

    <!-- 4.扫描web相关的bean -->
    <context:component-scan base-package="com.wang.book.controller"/>

</beans>
```

配置好web.xml后我们开始写控制层

**controller（控制层）**

控制层和前端有密不可分的关系，所以我们放在最后一起写

简单的说设置好请求和视图解析器，然后就可以在控制层根据请求执行响应的函数了hh



为了调试方便，引用logback（可以说是log4j的进化版）

```xml
<!-- 实现slf4j接口并整合 -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.1.1</version>
</dependency>
```

还有logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
         <!-- encoder 默认配置为PatternLayoutEncoder --> 

        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```



好了，开始控制层编写

**bookController.java**

```java
package com.wang.book.controller;


import com.wang.book.Exception.NoNumberException;
import com.wang.book.Exception.RepeatLendingException;
import com.wang.book.dto.LendingState;
import com.wang.book.dto.Result;
import com.wang.book.enums.LendingStateEnum;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import com.wang.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    //private Logger logger= LoggerFactory.getLogger(this.getClass());
    //controller层调用service层
    @Autowired
    private BookService bookService;

    //查询全部的书籍并发返回一个全部书籍的展示页面
    @RequestMapping("/list")
    public String list(Model model) {
        List<Books> list = bookService.getList();
        model.addAttribute("list", list);
        return "list";
    }

    //通过书籍名字搜索是否还有某图书
    @RequestMapping(value="/search",method = RequestMethod.POST)
    private void  search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //接收页面的值
        String name=req.getParameter("name");
        name=name.trim();//trim可以去掉string首位的空格，如果有的话
        //向页面传值
        req.setAttribute("name", name);
        req.setAttribute("list", bookService.getSomeList(name));
        req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
    }

    //查看某图书的详细情况
    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    private String detail(@PathVariable("bookId") int bookId, Model model){

        Books books = bookService.getById(bookId);
        if(books==null){
            return "forward:/books/list";
        }
        model.addAttribute("books",books);
        System.out.println(books);
        return "detail";
    }



    //验证输入的用户名、密码是否正确
    @RequestMapping(value="/verify", method = RequestMethod.POST, produces = {"application/json; charset=utf-8" })
    @ResponseBody
    private Map validate(int studentId, int password){//验证
        Map resultMap=new HashMap();
        Student student =null;
        System.out.println("验证函数");
        student =bookService.validateStu(studentId,password);

        System.out.println("输入的学号、密码："+studentId+":"+password);
        System.out.println("查询到的："+student.getStudentId()+":"+student.getPassword());

        if(student!=null){
            System.out.println("SUCCESS");
            resultMap.put("result", "SUCCESS");
            return resultMap;
        }else{
            resultMap.put("result", "FAILED");
            return resultMap;
        }

    }
    //执行预约的逻辑
    @RequestMapping(value = "/{bookId}/lending", method = RequestMethod.POST, produces = {"application/json; charset=utf-8" })
    @ResponseBody
    private Result<LendingState> execute(@PathVariable("bookId") int bookId, @RequestParam("studentId") int studentId){
        Result<LendingState> result;
        LendingState state=null;

        try{//手动try catch,在调用lendingState方法时可能报错
            state=bookService.lendingState(bookId, studentId);
            result=new Result<LendingState>(true,state);
            return result;

        } catch(NoNumberException e1) {
            state=new LendingState(bookId, LendingStateEnum.NO_NUMBER);
            result=new Result<LendingState>(true,state);
            return result;
        }catch(RepeatLendingException e2){
            state=new LendingState(bookId, LendingStateEnum.REPEAT_LENDING);
            result=new Result<LendingState>(true,state);
            return result;
        }catch (Exception e){
            state=new LendingState(bookId,LendingStateEnum.INNER_ERROR);
            result=new Result<LendingState>(true,state);
            return result;
        }
    }
    @RequestMapping(value ="/lending")
    private String lendingBooks(@RequestParam("studentId") int studentId,Model model){

        List<Lending> lendingList=new ArrayList<Lending>();
        lendingList=bookService.getLendingByStu(studentId);
        model.addAttribute("lendingList", lendingList);

        return "lendingBookList";
    }





}

```



为了把执行预约逻辑返回结果中的不同信息封装起来，我们创建一个Result类，它是类型T的集合。
**Result.java**

```
package com.wang.book.dto;


/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {       //泛型
    private boolean success;// 是否成功标志

    private T data;// 成功时返回的数据

    private String error;// 错误信息

    public Result() {
    }

    // 成功时的构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // 错误时的构造器
    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
```





为了给web显示预约后的反馈信息，我们建立一个常量数据字典类存放这些要反馈给客户的信息

枚举类的应用

**LendingStateEnum.java**

```java
package com.wang.book.enums;

import com.wang.book.pojo.Lending;

//使用枚举表述常量数据字典,我们先定义几个预约图书操作返回码的数据字典，也就是我们要返回给客户端的信息。
//和实体类差不多，枚举类也有成员变量，构造方法，get，set方法，不过构造器只能是私有化，这样可以保证外部代码无法新构造枚举类的实例。
public enum LendingStateEnum {
    //构造枚举值
    SUCCESS(1, "预约成功"), NO_NUMBER(0, "库存不足"), REPEAT_LENDING(-1, "重复预约"), INNER_ERROR(-2, "系统异常");

    private int state;

    private String stateInfo;

    private LendingStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static LendingStateEnum stateOf(int index) {
        for (LendingStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
```



最后，就是对应controller编写jsp页面

bootstrap，可以使用一些好看的（大概）模板

https://www.bootcss.com/p/layoutit/

```jsp
<!-- 引入 Bootstrap -->
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
```

以及js

```jsp
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
```



**list.jsp**

```jsp
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>图书列表</title>
    <%@include file="common/head.jsp" %>

    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>图书列表</h2>
        </div>
        <form name="firstForm" action="<%= request.getContextPath()%>/books/search" method="post">
            <div class="panel-heading ">
                <table class="table table-bookName">
                    <thead>
                    <tr>
                        <th width="90" align="lift">图书名称：</th>
                        <th width="150" align="lift">
                            <input type="text" name="name" class="allInput" value="${name1}" placeholder="请输入检索书名" />
                        </th>
                        <th>
                            <input type="submit" id="tabSub" value="检索" />
                        </th>
                    </tr>
                    </thead>
                </table>
            </div>
        </form>


        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>馆藏数量</th>
                    <th>详细介绍</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="sk">
                    <tr>
                        <td>${sk.bookId}</td>
                        <td>${sk.bookName}</td>
                        <td>${sk.number}</td>
                        <td><a class="btn btn-info" href="/books/${sk.bookId}/detail " target="_blank">详细</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->

<script src="${pageContext.request.contextPath}/statics/js/jquery-3.5.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>
```

**detail.jsp**

```jsp
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>预约详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>图书详情</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>图书简介</th>
                    <th>剩余数量</th>
                    <th>预约数量</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${books.bookId}</td>
                    <td>${books.bookName}</td>
                    <td>${books.detail}</td>
                    <td>${books.number}</td>
                    <td>1</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="panel-body text-center">
            <h2 class="text-danger">
                <!--用来展示预约控件-->
                <span class="glyphicon" id="lending-box"></span> <!--在js里面调用这个id还可以动态显示一些其他东西，例如动态时间等（需要插件）-->

                <span class="glyphicon"><a class="btn btn-primary btn-lg" href="/books/lending?studentId=${cookie['studentId'].value}" target="_blank">查看我已预约的书籍</a></span>
            </h2>           <!--如何获取该页面弹出层输入的学生ID， 传给上面的url-->
        </div>


    </div>

</div>
<!--  登录弹出层 输入对话   -->
<div id="varifyModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-studentId"> </span>请输入学号和密码:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="studentId" id="studentIdKey"
                               placeholder="填写学号" class="form-control">
                    </div>
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="password" name="password" id="passwordKey"
                               placeholder="输入密码" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <!--  验证信息 -->
                <span id="studentMessage" class="glyphicon"> </span>
                <button type="button" id="studentBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-student"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>
<%--jQery文件,务必在bootstrap.min.js之前引入--%>
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>


<script src="${pageContext.request.contextPath}/script/booklending.js"></script>


<script type="text/javascript">
    $(function () {
        //使用EL表达式传入参数
        booklending.detail.init({
            bookId:${books.bookId}

        });
    })
</script>
</html>
```

用cookie可以存储密码，利用ajax刷新页面并起到一个拦截器作用，详见bookLending.js

点击详情，进入预定页面，如果没有登陆的话会弹出登陆层，提示输入学号密码，如果用户已经在之前查看某本书的详细时已经登陆，则不会要求再登陆，这里的用户名密码与数据库验证成功后保存在cookie中，且生命周期默认与session相同，即关闭路径窗口后就失效。

**lendingBookList.jsp**

```jsp
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>预约图书列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>您已预约图书列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>预定学号</th>
                    <th>预约时间</th>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>图书简介</th>
                    <th>预约数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${lendingList}" var="sk">
                    <tr>
                        <td>${sk.studentId}</td>
                        <td>${sk.lendingTime}</td>
                        <td>${sk.bookId}</td>
                        <td>${sk.books.getBookName()}</td>
                        <td>${sk.books.getDetail()}</td><!--实际上对应Books类的方法-->
                        <td>1</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<!--  Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>
```

关于报错：

如果报错400，

 `The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).`

可能是前端的请求类型和controller层的数据类型不一样，

也可能是booklending.js文件载入失败，请注意自己项目中js的位置

```jsp
<script src="${pageContext.request.contextPath}/script/booklending.js"></script>
```



然后一些优化，需要引用一些js

见common下的文件head.jsp和tag.jsp



-2021-02-17



**添加还书功能**

预计实现：点击还书，可以将借阅表lending中对应的记录删除并更新books表中的图书数量



其实实现思路应该和实现预约差不多，前端完全可以模仿着构造，主要是底层，给项目添加功能就像

建造宫殿，最好还是从地基（底层）开始..不过这不妨碍我们设想一下前端的操作可行性

还书按钮可以放在detail.jsp，在访问的时候在登录后查看已预约书籍，图书详情下可以添加“还书

，和预约键相同，用ajax，js绑定，目前计划是这样



好，假设returning为还书属性，还书操作其实只需要学生id（studentId），图书id（bookId）就可以实现，实体类中pojo下无需新增，需要更新的应该是BookMapper，LendingMapper，BookService，BookServiceImpl，BookController，主要是新增还书这个函数以及相关实现

BookMapper.java

```
//用于还书,主要更新books表
int increaseNumber(int bookId);
```

BookMapper.xml

更新的sql语句格式参考：

UPDATE `books` set number = number+1 where bookId = xxx;

```xml
<update id="increaseNumber">
   UPDATE books
   SET number = number + 1
   WHERE
      bookID = #{bookId}
   AND number >= 0
</update>
```



LendingMapper.java

```java
//通过图书ID和学生ID返还书籍
int Returning(@Param("bookId") int bookId, @Param("studentId") int studentId);
```

LendingMapper.xml

```xml
<delete id="Returning"  parameterType="com.wang.book.pojo.Books">
    delete from lending where bookID = #{bookId} and student_id = #{studentId}
</delete>
```



到了业务层才发现对应service层比较麻烦，还书操作其实也应该有对应的异常，直接套用借阅的类和异常类还不太行。

```java
public int increaseNumber(int bookId) {
    return bookMapper.increaseNumber(bookId);
}


public int Returning( int bookId, int studentId){
    return lendingMapper.Returning(bookId,studentId);
}
```



控制层

BookController.java



```
@RequestMapping(value ="/{bookId}/returning")
private String returningBooks(@PathVariable("bookId") int bookId, @RequestParam("studentId") int studentId,Model model){

    bookService.increaseNumber(bookId);
    bookService.Returning(bookId,studentId);



    return "redirect:/books/detail";
}
```

然后修改booklending.js

```xml
jQuery.ajax(...)
      部分参数：
            url：请求地址
            type：请求方式，GET、POST（1.9.0之后用method）
        headers：请求头
            data：要发送的数据
    contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
          async：是否异步
        timeout：设置请求超时时间（毫秒）
      beforeSend：发送请求前执行的函数(全局)
        complete：完成之后执行的回调函数(全局)
        success：成功之后执行的回调函数(全局)
          error：失败之后执行的回调函数(全局)
        accepts：通过请求头发送给服务器，告诉服务器当前客户端可接受的数据类型
        dataType：将服务器端返回的数据转换成指定类型
          "xml": 将服务器端返回的内容转换成xml格式
          "text": 将服务器端返回的内容转换成普通文本格式
          "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行。
        "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
          "json": 将服务器端返回的内容转换成相应的JavaScript对象
        "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数
```



booklending.js

```js
	returning:function(bookId,studentId, node2){
		console.log("我执行还书的方法!" );
		node2.html('<button class="btn btn-primary btn-lg" id="returningBtn">还书</button>');

		var returningUrl = booklending.URL.returning(bookId,studentId);
		console.log("returningUrl:"+returningUrl);
		//绑定一次点击事件
		$('#returningBtn').one('click', function () {
			//执行还书请求
			//1、先禁用请求
			$(this).addClass('disabled');
			//2、发送还书请求执行还书操作
			$.post(returningUrl,{},function(result){
				if(result && result['success']){
					/*var returningResult=result['data'];
					console.log("returningResult"+returningResult);
					var state=returningResult['state'];
					console.log("state"+state);
					var stateInfo=returningResult['stateInfo'];
					console.log("stateInfo"+stateInfo);*/
					var state1="还书成功";
					//显示还书结果

					node2.html('<span class="label label-success">'+state1+'</span>');
				}      
				console.log('result'+result);
			});
		});


	}
```



然后就是前端，在detail.jsp中添加，为了美观可以放置在"查看我已预约书籍"这个右侧

```
<span class="glyphicon" id="returning-box"></span>
```



为了方便返回所以加了一些按钮：

```
<div class="col-md-4 column">

    <a class="btn btn-primary" href="${pageContext.request.contextPath}/books/list">返回图书列表</a>
</div>
```