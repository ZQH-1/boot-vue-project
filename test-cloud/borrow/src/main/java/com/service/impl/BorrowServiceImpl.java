package com.service.impl;

import com.client.BookClient;
import com.client.UserClient;
import com.entity.UserBorrowDetail;
import com.mapper.BorrowMapper;
import com.service.BorrowService;
import entity.Book;
import entity.Borrow;
import entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BorrowServiceImpl implements BorrowService {
    @Resource
    BorrowMapper borrowMapper;
    @Resource
    UserClient userClient;
    @Resource
    BookClient bookClient;


    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrows=borrowMapper.getBorrowByUid(uid);
        User user=userClient.getUserByUid(uid);
        List<Book> books=borrows.stream().map(b->bookClient.getBookByBid(b.getBid())).collect(Collectors.toList());
        return new UserBorrowDetail(user,books);
    }
}
