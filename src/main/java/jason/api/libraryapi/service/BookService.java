package jason.api.libraryapi.service;

import jason.api.libraryapi.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> listAllBooks();

    Book addNewBook (Book book);

    Book findBookById (Long id);

    void deleteBookById (Long id);
}
