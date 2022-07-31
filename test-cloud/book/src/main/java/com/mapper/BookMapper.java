package com.mapper;

import entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookMapper {

    @Select("select * from db_book where bid=${bid}")
    Book getBookByBid(int bid);
}
