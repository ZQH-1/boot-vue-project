package com.controller;

import com.service.BookService;
import entity.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BookController {
    @Resource
    BookService bookService;
    @RequestMapping("/book/{bid}")
    public Book getBookByBid(@PathVariable("bid") int bid){
        return bookService.getBookByBid(bid);
    }
}
