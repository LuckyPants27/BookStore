package ru.luckypants.bookstore.repository.datasource;

import ru.luckypants.bookstore.model.*;

import java.util.ArrayList;
import java.util.List;

public class BookDataSourceImpl implements BookDataSource {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public BookDataSourceImpl() { initData(); }

    private void initData() {
        // продавцы
        employees.add(new Employee( 1, "Иванов Иван", 32));
        employees.add(new Employee(2,"Петров Петр",30));
        employees.add(new Employee(3,"Васильева Алиса",26));

        // покупатели
        customers.add(new Customer(1,"Сидоров Алексей",25));
        customers.add(new Customer(2,"Романов Дмитрий",32));
        customers.add(new Customer(3,"Симонов Кирилл", 19));
        customers.add(new Customer(4,"Кириенко Данил",45));
        customers.add(new Customer(5,"Воротынцева Элина",23));

        // книги
        books.add(new Book(1, "Война и Мир", "Толстой Лев",1600, BookGenre.Art));
        books.add(new Book(2,"Преступление и наказание","Достоевский Федор",600, BookGenre.Art));
        books.add(new Book(3,"Мертвые души","Гоголь Николай",750, BookGenre.Art));
        books.add(new Book(4,"Руслан и Людмила","Пушкин Александр",500, BookGenre.Art));

        books.add(new Book(5,"Введение в психоанализ","Фрейд Зигмунд",1050, BookGenre.Psychology));
        books.add(new Book(6,"Психология влияния. Убеждай. Воздействуй. Защищайся.","Чалдини Роберт",550, BookGenre.Psychology));
        books.add(new Book(7,"Как перестать беспокоиться и начать жить","Карнеги Дейл",1000, BookGenre.Psychology));

        books.add(new Book(8,"Gang of Four","Гамма Эрих",900, BookGenre.Programming));
        books.add(new Book(9,"Совершенный код","Макконел Стив",1200, BookGenre.Programming));
        books.add(new Book(10,"Рефакторинг. Улучшение существующего кода","Фаулер Мартин",850, BookGenre.Programming));
        books.add(new Book(11,"Алгоритмы. Построение и анализ","Кормен Томас Х.",450, BookGenre.Programming));

        // заказы
        orders.add(new Order(1,1,1, new long[]{8,9,10,11}));
        orders.add(new Order(2,1,2, new long[]{1}));

        orders.add(new Order(3,2,3, new long[]{5,6,7}));
        orders.add(new Order(4,2,4, new long[]{1,2,3,4}));

        orders.add(new Order(5,3,5, new long[]{2,5,9}));
    }

    @Override
    public List<Book> getBooks() { return books; }

    @Override
    public List<Customer> getCustomers() { return customers; }

    @Override
    public List<Employee> getEmployees() { return employees; }

    @Override
    public List<Order> getOrders() { return orders; }
}
