package ru.luckypants.bookstore.repository;

import ru.luckypants.bookstore.model.*;
import ru.luckypants.bookstore.repository.datasource.BookDataSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class BookRepositoryImpl implements BookRepository {
    private final BookDataSource bookDataSource;

    public BookRepositoryImpl(BookDataSource bookDataSource) {
        this.bookDataSource = bookDataSource;
    }

    /**
     * Получить наиболее популярный жанр среди покупателей до возраста #age
     * @param age требуемый возраст
     * @return наиболее популярный жанр
     */
    public BookGenre getMostPopularGenreUnderAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<>();

        for (Customer customer : bookDataSource.getCustomers()) {
            if (customer.getAge() < age) {
                customersIds.add(customer.getId());
            }
        }

        return getMostPopularGenre(customersIds);
    }

    /**
     * Получить наиболее популярный жанр среди покупателей старше возраста #age
     * @param age требуемый возраст
     * @return наиболее популярный жанр
     */
    public BookGenre getMostPopularGenreOverAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<>();

        for (Customer customer : bookDataSource.getCustomers()) {
            if (customer.getAge() > age) {
                customersIds.add(customer.getId());
            }
        }

        return getMostPopularGenre(customersIds);
    }

    private BookGenre getMostPopularGenre(ArrayList<Long> customersIds) {
        int countArt = 0, countPr = 0, countPs = 0;

        for (Order order : bookDataSource.getOrders()) {
            if (customersIds.contains(order.getCustomerId())) {
                countArt += getCountOfSoldBooksByGenre(order,BookGenre.Art);
                countPr += getCountOfSoldBooksByGenre(order,BookGenre.Programming);
                countPs += getCountOfSoldBooksByGenre(order,BookGenre.Psychology);
            }
        }

        ArrayList<BookAdditional> result = new ArrayList<>();
        result.add(new BookAdditional(BookGenre.Art,countArt));
        result.add(new BookAdditional(BookGenre.Programming,countPr));
        result.add(new BookAdditional(BookGenre.Psychology,countPs));

        result.sort(new Comparator<BookAdditional>() {
            @Override
            public int compare(BookAdditional left, BookAdditional right) {
                return right.getCount() - left.getCount();
            }
        });

        return result.get(0).getGenre();
    }

    // получить количество проданных книг по жанрам
    public ArrayList<BookAdditional> getCountOfSoldBooksByGenre() {
        ArrayList<BookAdditional> result = new ArrayList<>();
        int countArt = 0, countPr = 0, countPs = 0;
        for (Order order : bookDataSource.getOrders()) {
            countArt += getCountOfSoldBooksByGenre(order,BookGenre.Art);
            countPs += getCountOfSoldBooksByGenre(order,BookGenre.Psychology);
            countPr += getCountOfSoldBooksByGenre(order,BookGenre.Programming);
        }

        result.add(new BookAdditional(BookGenre.Art,countArt));
        result.add(new BookAdditional(BookGenre.Programming,countPr));
        result.add(new BookAdditional(BookGenre.Psychology,countPs));

        return result;
    }

    /**
     * Получить количество книг в одном заказе по определенному жанру
     * @param order заказ
     * @param genre жанр
     * @return количество книг
     */
    public int getCountOfSoldBooksByGenre(Order order, BookGenre genre) {
        int count = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre)
                count++;
        }

        return count;
    }

    // получить стоимость проданных книг по жанрам
    public HashMap<BookGenre,Double> getPriceOfSoldBooksByGenre() {
        HashMap<BookGenre,Double> result = new HashMap<>();
        double priceArt = 0, pricePr = 0, pricePs = 0;

        for (Order order : bookDataSource.getOrders()) {
            priceArt += getPriceOfSoldBooksByGenre(order, BookGenre.Art);
            pricePr += getPriceOfSoldBooksByGenre(order, BookGenre.Programming);
            pricePs += getPriceOfSoldBooksByGenre(order, BookGenre.Psychology);
        }

        result.put(BookGenre.Art, priceArt);
        result.put(BookGenre.Programming, pricePr);
        result.put(BookGenre.Psychology, pricePs);

        return result;
    }
    /**
     * Получить общую сумму книг в одном заказе по определенному жанру
     * @param order заказ
     * @param genre жанр
     * @return общая стоимость
     */
    public double getPriceOfSoldBooksByGenre(Order order, BookGenre genre) {
        double price = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre)
                price += book.getPrice();
        }

        return price;
    }
    /**
     * Получить общее количество и общую стоимость проданного товара для продавца
     * @param employeeId уникальный номер продавца
     * @return количство и общая стоимость указанного продавца
     */
    public Profit getProfitByEmployee(long employeeId) {
        int count = 0; double price =0;
        for (Order order : bookDataSource.getOrders()) {
            if (order.getEmployeeId() == employeeId) {
                price += getPriceOfSoldBooksInOrder(order);
                count += order.getBooks().length;
            }
        }

        return new Profit(count,price);
    }
    // получить общую сумму заказов
    public double getAllPriceOfSoldBooks() {
        double price = 0;
        for (Order order : bookDataSource.getOrders()) {
            price += getPriceOfSoldBooksInOrder(order);
        }

        return price;
    }
    /**
     * получить общуюю стоимость одного заказа
     * @param order заказ, по которому считается стоимость
     * @return общая стоимость для всех проданных книг в заказе
     */
    public double getPriceOfSoldBooksInOrder(Order order) {
        double price = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null)
                price += book.getPrice();
        }

        return price;
    }
    // получить общее количество проданных книг
    public int getCountOfSoldBooks() {
        int count = 0;
        for (Order order : bookDataSource.getOrders()) {
            count += order.getBooks().length;
        }

        return count;
    }

    /**
     * Поиск книги в списке книг по ее уникальному номеру
     * @param id уникальный номер книги
     * @return найденная книга
     */
    public Book getBookById(long id) {
        Book current = null;

        for (Book book : bookDataSource.getBooks()) {
            if (book.getId() == id) {
                current = book;
                break;
            }
        }
        return current;
    }
}
