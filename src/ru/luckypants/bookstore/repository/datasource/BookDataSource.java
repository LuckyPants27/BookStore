package ru.luckypants.bookstore.repository.datasource;

import ru.luckypants.bookstore.model.Book;
import ru.luckypants.bookstore.model.Customer;
import ru.luckypants.bookstore.model.Employee;
import ru.luckypants.bookstore.model.Order;

import java.util.List;

public interface BookDataSource {
    List<Book> getBooks();
    List<Customer> getCustomers();
    List<Employee> getEmployees();
    List<Order> getOrders();
}
