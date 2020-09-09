package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.exception.ResourceNotFoundException;
import jason.api.libraryapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    // get all books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> retrieveAllBooks() {
        List<Book> list = bookRepository.findAll();
        return new ResponseEntity<List<Book>>(list, HttpStatus.OK);
    }

    // get a book by ID
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> retrieveBookById(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id:" + bookId));
        return ResponseEntity.ok().body(book);
    }

    //post a book
    @PostMapping("/books")
    public Book addNewBook(@Validated @RequestBody Book book) {
        return bookRepository.save(book);
    }

    //patch a book by ID
    @PatchMapping("/books/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id,
                                          @RequestBody Book bookDetails) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book not found for id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }


    //delete a book by ID
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id:" + bookId));
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }


}
