package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BookController.BASE_URL)
public class BookController {

    public static final String BASE_URL = "/library/books";

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> retrieveAllBooks() {
        return bookService.listAllBooks();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Book retrieveBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addNewBook(@RequestBody Book book) {
        return bookService.addNewBook(book);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Book updateExistingBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.patchBook(id, book);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long id)  {
        bookService.deleteBookById(id);
    }
}
