package jason.api.libraryapi.service;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    public Book addNewBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

}
