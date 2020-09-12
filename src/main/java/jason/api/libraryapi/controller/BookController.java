package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.exception.ResourceNotFoundException;
import jason.api.libraryapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/books")
public class BookController {

    @Autowired
    private BookService bookService;

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
    public ResponseEntity<Book> updateExistingBook(@PathVariable(value = "id") Long bookId,
                                          @RequestBody Book bookDetails) throws ResourceNotFoundException {
        Book book = bookService.findBookById(bookId);
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
