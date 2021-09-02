package ru.luckypants.bookstore.ui;

import ru.luckypants.bookstore.repository.BookRepository;
import ru.luckypants.bookstore.repository.BookRepositoryImpl;
import ru.luckypants.bookstore.repository.datasource.BookDataSource;
import ru.luckypants.bookstore.repository.datasource.BookDataSourceImpl;


public class Main {

    public static void main(String[] args) {
        BookDataSource bookDataSource = new BookDataSourceImpl();
        BookRepository bookRepository = new BookRepositoryImpl(bookDataSource);
        BookAdapter adapter = new BookAdapter(bookRepository, bookDataSource);
        adapter.show();
    }



}
