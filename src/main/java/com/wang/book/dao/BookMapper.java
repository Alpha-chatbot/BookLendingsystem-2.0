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

    //用于还书,主要更新books表
    int increaseNumber(int bookId);

}
