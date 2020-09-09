package jason.api.libraryapi.controller;

import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.repositories.BookRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@RunWith(SpringRunner.class)
public class BookControllerTest extends AbstractRestControllerTest {

    @MockBean
    BookRepository bookRepository;

    @MockBean
    BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(bookRepository)
//                .build();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

    }

    @Test
    public void testListBooks() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/library/books"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json("[]"));
//        Mockito.verify(bookRepository, Mockito.times(1)).findAll();



//        given
        Book testBook = new Book();
        testBook.setTitle("book1");
        testBook.setAuthor("Author1");

        Book testBook2 = new Book();
        testBook2.setTitle("book1");
        testBook2.setAuthor("Author1");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook, testBook2));

        mockMvc.perform(MockMvcRequestBuilders.get("/library/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title", hasSize(2)));

    }











}