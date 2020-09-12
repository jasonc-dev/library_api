package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.exception.ResourceNotFoundException;
import jason.api.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Book>> retrieveAllBooks() {
        List<Book> list = bookService.listAllBooks();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Book> retrieveBookById(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        Book book = bookService.findBookById(bookId);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping
    public Book addNewBook(@RequestBody Book book) {
        return bookService.addNewBook(book);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<Book> updateExistingBook(@PathVariable Long id,
                                          @RequestBody Book bookDetails) throws ResourceNotFoundException {
        Book book = bookService.findBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        bookService.addNewBook(book);
        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
        bookService.findBookById(id);
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}
