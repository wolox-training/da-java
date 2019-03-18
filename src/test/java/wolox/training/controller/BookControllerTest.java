package wolox.training.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private BookRepository bookRepository;


    private Book getBook(){
        Book book = new Book();
        book.setAuthor("Darwin");
        book.setGenre("Romantic");
        book.setImage("/user/book.jpg");
        book.setIsbn("AV504");
        book.setPages(23);
        book.setTitle("Wild Age");
        book.setPublisher("Araque");
        book.setSubtitle("Try out");
        book.setYear("2015");
        return book;
    }

    @Test
    public void findAllTest() throws Exception {
        Book bookOneSaved = getBook();
        Book bookTwoSaved = getBook();
        List<Book> books = Arrays.asList(bookOneSaved, bookTwoSaved);

        given(bookRepository.findAll()).willReturn(books);

            mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findOneTest() throws Exception {
        Book bookSaved = getBook();
        given(bookRepository.findById(1l)).willReturn(Optional.of(bookSaved));
        mvc.perform(get("/api/books/{id}", 1l))
            .andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        Book book = getBook();

        mvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }



    @Test
    public void notCreateTest() throws Exception {
        Book book = new Book();
        book.setTitle("title");

        mvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }
}

