package com.entity;

import entity.Book;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBorrowDetail {
    User user;
    List<Book> bookList;
}
