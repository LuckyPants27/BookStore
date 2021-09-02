package ru.luckypants.bookstore.repository;

import ru.luckypants.bookstore.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface BookRepository {
    int getCountOfSoldBooks();
    double getAllPriceOfSoldBooks();
    Profit getProfitByEmployee(long employeeId);
    ArrayList<BookAdditional> getCountOfSoldBooksByGenre();
    HashMap<BookGenre, Double> getPriceOfSoldBooksByGenre();
    BookGenre getMostPopularGenreUnderAge(int age);
    BookGenre getMostPopularGenreOverAge(int age);
}
