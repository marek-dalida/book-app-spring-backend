package com.books.zpt3.sevices;

import com.books.zpt3.models.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class DashboardService {

    static ArrayList<Book> books;

    public DashboardService() {
        books = new ArrayList<Book>() {{
            add(new Book(1, "Tytuł_1", "Author_1", 2000));
            add(new Book(2, "Tytuł_2", "Author_2", 2000));
            add(new Book(3, "Tytuł_3", "Author_3", 2000));
            add(new Book(4, "Tytuł_4", "Author_4", 2000));
            add(new Book(5, "Tytuł_5", "Author_5", 2000));
        }};
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Book deleteBook(Integer id) {
        Book deletedBook = books.stream()
                .filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        books.removeIf(book -> book.getId() == id);
        return deletedBook;
    }

    public Book getBookById(Integer id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Book createBook(Book newBook) {
        Book maxBook = Collections.max(books);
        int newBookId = maxBook.getId() + 1;
        newBook.setId(newBookId);

        books.add(newBook);
        return newBook;
    }


}
