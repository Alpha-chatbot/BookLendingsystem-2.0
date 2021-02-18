package com.wang.book.service;

import java.util.List;

import com.wang.book.Exception.LendingException;
import com.wang.book.Exception.NoNumberException;
import com.wang.book.Exception.RepeatLendingException;
import com.wang.book.dao.BookMapper;
import com.wang.book.dao.LendingMapper;
import com.wang.book.dao.StudentMapper;
import com.wang.book.dto.LendingState;
import com.wang.book.dto.ReturningState;
import com.wang.book.enums.LendingStateEnum;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import org.apache.ibatis.annotations.Param;
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




    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public void setLendingMapper(LendingMapper lendingMapper) {
        this.lendingMapper = lendingMapper;
    }

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
    @Transactional//发生异常后可保存回滚
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


    public int increaseNumber(int bookId) {
        return bookMapper.increaseNumber(bookId);
    }


    public int Returning( int bookId, int studentId){
        return lendingMapper.Returning(bookId,studentId);
    }



/*    @Override
    @Transactional//发生异常后可保存回滚
    public LendingState returning(int bookId, int studentId) {//在Dao的基础上组织逻辑，形成与web成交互用的方法
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
    }*/

}