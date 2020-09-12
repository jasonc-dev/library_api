package jason.api.libraryapi.service;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    BookService bookService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void listAllBooks() throws Exception {
        //given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Title1");
        book1.setAuthor("Author1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Title2");
        book2.setAuthor("Author2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        //when
        List<Book> books = bookService.listAllBooks();

        //then
        assertEquals(2, books.size());
    }

    @Test
    public void findBookById() throws Exception {
        //given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Title1");
        book1.setAuthor("Author1");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book1));

        //when
        Book book = bookService.findBookById(1L);

        assertEquals("Title1", book.getTitle());
    }

    @Test
    public void addNewBook() throws Exception {
        //given
        Book book = new Book();
        book.setTitle("Title1");
        book.setAuthor("Author1");

        Book savedBook = new Book();
        savedBook.setTitle(book.getTitle());
        savedBook.setAuthor(book.getAuthor());
        savedBook.setId(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        //when
        Book anotherBook = bookService.addNewBook(book);

        //then
        assertEquals(book.getTitle(), anotherBook.getTitle());
        assertEquals(book.getAuthor(), anotherBook.getAuthor());

    }


    @Test
    public void deleteBookById() throws Exception {

        Long id = 1L;

        bookRepository.deleteById(id);

        verify(bookRepository, times(1)).deleteById(anyLong());
    }
}