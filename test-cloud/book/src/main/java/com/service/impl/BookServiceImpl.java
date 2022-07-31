package com.service.impl;

import com.mapper.BookMapper;
import com.service.BookService;
import entity.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class BookServiceImpl implements BookService {
    @Resource
    BookMapper bookMapper;

    @Override
    public Book getBookByBid(int bid) {
        return bookMapper.getBookByBid(bid);
    }
}
