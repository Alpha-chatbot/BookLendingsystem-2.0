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