package com.wang.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lending {

    private int bookId;
    private int studentId;
    private Date lendingTime;
    private Books books;
}
