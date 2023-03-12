package com.books.zpt3.controllers;


import com.books.zpt3.models.Book;
import com.books.zpt3.sevices.DashboardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DashboardController {

    DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> list = dashboardService.getBooks();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/dashboard/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id, Principal principal) {
        Book book = dashboardService.getBookById(id);

        HttpHeaders headers = new HttpHeaders();

        if (book == null) {
            headers.add("Exception-Message", "Book with id= " + id + " not found");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @RequestMapping(value = "/dashboard/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Integer id) {
        Book deletedBook = dashboardService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();

        if (deletedBook == null) {
            headers.add("Exception-Message", "Book to delete with id= " + id + " not found");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        headers.add("Message", "Book with id= " + id + " successfully deleted");
        return new ResponseEntity<>(deletedBook, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Book> createBook(@RequestBody Book model) {
        Book addedBook = dashboardService.createBook(model);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Message", "Book with id= " + addedBook.getId() + " successfully added");
        return new ResponseEntity<>(addedBook, headers, HttpStatus.CREATED);
    }

}
