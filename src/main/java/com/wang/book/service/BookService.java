package com.wang.book.service;

import com.wang.book.dto.LendingState;
import com.wang.book.dto.ReturningState;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import org.apache.ibatis.annotations.Param;

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



        int increaseNumber(int bookId);
        int Returning(@Param("bookId") int bookId, @Param("studentId") int studentId);


        //ReturningState returningState(int bookId, int studentId);

    }

