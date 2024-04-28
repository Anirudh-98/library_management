package com.example.app.service;

import com.example.app.dao.BookRepo;
import com.example.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;


    public Book addNewBook(Book book){
        return bookRepo.save(book);
    }

    public List<Book> getAllBooks(){
        return (List<Book>) bookRepo.findAll();
    }

    public Book getBookDetailsById(Integer bookId){
        return bookRepo.findById(bookId).get();
    }

    public Book updateBook(Integer  bookId, Book book){
        book.setBookId(bookId);
        return bookRepo.save(book);
    }

    public void deleteBookDetails(Integer bookId){
        bookRepo.deleteById(bookId);
    }

}
