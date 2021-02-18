package com.wang.book.controller;


import com.wang.book.Exception.NoNumberException;
import com.wang.book.Exception.RepeatLendingException;
import com.wang.book.dto.LendingState;
import com.wang.book.dto.Result;
import com.wang.book.dto.ReturningState;
import com.wang.book.enums.LendingStateEnum;
import com.wang.book.pojo.Books;
import com.wang.book.pojo.Lending;
import com.wang.book.pojo.Student;
import com.wang.book.service.BookService;
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

    @RequestMapping(value ="/{bookId}/returning")
    private String returningBooks(@PathVariable("bookId") int bookId, @RequestParam("studentId") int studentId,Model model){

        bookService.increaseNumber(bookId);
        bookService.Returning(bookId,studentId);


        return "detail";
    }





}
