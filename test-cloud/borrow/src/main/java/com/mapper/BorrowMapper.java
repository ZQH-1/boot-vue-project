package com.mapper;

import entity.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowMapper {
    @Select("select * from db_borrow where uid=#{uid} and bid=#{bid}")
    Borrow getBorrow(int uid,int bid);
    @Select("select * from db_borrow where uid=${uid}")
    List<Borrow> getBorrowByUid(int uid);
    @Select("select * from db_borrow where bid=${bid}")
    Borrow getBorrowByBid(int ubid);

}
