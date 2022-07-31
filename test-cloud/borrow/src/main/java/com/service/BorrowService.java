package com.service;

import com.entity.UserBorrowDetail;
import entity.Borrow;

public interface BorrowService {

    UserBorrowDetail getUserBorrowDetailByUid(int uid);
}
