package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.exception.RestResponseEntityExceptionHandler;
import jason.api.libraryapi.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void retrieveAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Title1");
        book1.setAuthor("Author1");

        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setAuthor("Author2");

        when(bookService.listAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/library/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(2)));
    }

    @Test
    public void retrieveBookById() {
    }

    @Test
    public void addNewBook() {
    }

    @Test
    public void updateExistingBook() {
    }

    @Test
    public void deleteBook() {
    }
}