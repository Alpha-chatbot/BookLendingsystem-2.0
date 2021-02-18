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

    //通过图书ID和学生ID返还书籍
    int Returning(@Param("bookId") int bookId, @Param("studentId") int studentId);

}
