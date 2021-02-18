package com.wang.book.dao;

import com.wang.book.pojo.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {


    /* === 学生相关 ===*/
    //向数据库验证输入的密码是否正确

    Student queryStudent(@Param("studentId") int studentId, @Param("password") int password);

}
