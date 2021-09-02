package ru.luckypants.bookstore.ui;

import ru.luckypants.bookstore.model.BookAdditional;
import ru.luckypants.bookstore.model.BookGenre;
import ru.luckypants.bookstore.model.Employee;
import ru.luckypants.bookstore.repository.BookRepository;
import ru.luckypants.bookstore.repository.datasource.BookDataSource;

import java.util.ArrayList;
import java.util.HashMap;

public class BookAdapter {
    private final BookRepository repository;
    private final BookDataSource bookDataSource;

    public BookAdapter(BookRepository repository, BookDataSource bookDataSource) {
        this.repository = repository;
        this.bookDataSource = bookDataSource;
    }

    public void show() {
        // Задача № 1
        String booksInfo =
                String.format("Общее количество проданных книг %d на сумму %f", repository.getCountOfSoldBooks(),repository.getAllPriceOfSoldBooks());
        System.out.println(booksInfo);

        // Задача № 2
        for (Employee employee : bookDataSource.getEmployees()) {
            System.out.println(employee.getName() +" продал(а) "+repository.getProfitByEmployee(employee.getId()).toString());
        }

        // Задача № 3
        ArrayList<BookAdditional> soldBooksCount = repository.getCountOfSoldBooksByGenre();
        HashMap<BookGenre,Double> soldBooksPrices = repository.getPriceOfSoldBooksByGenre();

        String soldBooksStr = "По жанру %s продано %d книг(и) общей стоимостью %f";

        for(BookAdditional bookAdditional : soldBooksCount) {
            double price = soldBooksPrices.get(bookAdditional.getGenre());
            System.out.println(
                    String.format(
                            soldBooksStr,
                            bookAdditional.getGenre().name(),bookAdditional.getCount(),price));
        }

        // Задача № 4
        int age = 30;
        String analyzeGenreStr = "Покупатели младше %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr,age, repository.getMostPopularGenreUnderAge(age)));

        // Задача № 5
        String analyzeGenreStr2 = "Покупатели старше %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr2,age, repository.getMostPopularGenreOverAge(age)));

    }
}
