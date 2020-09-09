package jason.api.libraryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jason.api.libraryapi.domain.Book;
import jason.api.libraryapi.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
        Mockito.verify(bookService, Mockito.times(1)).listAllBooks();
    }


    @Test
    public void testAddNewBook() throws Exception {

        Book newTestBook = new Book();
        newTestBook.setId((long) 1);
        newTestBook.setTitle("title1");
        newTestBook.setAuthor("author1");

        String inputInJson = this.mapToJson(newTestBook);

        String URI = "library/books/1";

        Mockito.when(bookService.addNewBook(Mockito.any(Book.class))).thenReturn(newTestBook);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        AssertionErrors.assertEquals("test", HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testFindBookById() throws Exception {

        Book newTestBook = new Book();
        newTestBook.setId((long) 1);
        newTestBook.setTitle("title1");
        newTestBook.setAuthor("author1");

        String inputInJson = this.mapToJson(newTestBook);

        Mockito.when(bookService.findBookById(Mockito.anyLong())).thenReturn(newTestBook);

        String URI = "library/books/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(newTestBook);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    public void testListAllBooks() throws Exception {

        Book newTestBook = new Book();
        newTestBook.setId((long) 1);
        newTestBook.setTitle("title1");
        newTestBook.setAuthor("author1");

        Book newTestBook2 = new Book();
        newTestBook2.setId((long) 2);
        newTestBook2.setTitle("title2");
        newTestBook2.setAuthor("author2");

        List<Book> bookList = new ArrayList<>();
        bookList.add(newTestBook);
        bookList.add(newTestBook2);

        Mockito.when(bookService.listAllBooks()).thenReturn(bookList);

        String URI = "/library/books";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(bookList);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);

    }


    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}