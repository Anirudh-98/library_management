package com.example.app.controller;

import com.example.app.dao.BookRepo;
import com.example.app.entity.Book;
import com.example.app.entity.ImageModel;
import com.example.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BookService bookService;
    @PostMapping(value = {"/addNewBook"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Book addNewProduct(@RequestPart("book") Book book,
                              @RequestPart("imageFile")MultipartFile[] file
                              ){
        try{
            Set<ImageModel> images = uploadImage(file);
            book.setBookImages(images);
            return bookService.addNewBook(book);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file: multipartFiles){
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }

   @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return  new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(value ="/getBookDetailsById/{bookId}")
    public Book getProductDetailsById(@PathVariable("bookId") Integer bookId) {
        return bookService.getBookDetailsById(bookId);
    }

    @PutMapping(value ="/updateBook/{bookId}")
    public Book updateBook(@PathVariable("id") Integer bookId,@RequestBody Book book){
        return bookService.updateBook(bookId,book);
    }

    @DeleteMapping(value ="/deleteBook/{bookId}")
    public void deleteBookDetails(@PathVariable("bookId") Integer bookId){
        bookService.deleteBookDetails(bookId);
    }

}
