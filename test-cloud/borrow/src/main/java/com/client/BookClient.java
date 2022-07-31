package com.client;

import entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "http://localhost:8020",name = "book-service")
public interface BookClient {
    @RequestMapping("/book/{bid}")
    public Book getBookByBid(@PathVariable("bid") int bid);
}
