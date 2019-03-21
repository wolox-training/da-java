package wolox.training.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.Utils;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private UserRepository userRepository;


    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private User user;

    private User getUser(){
        User user = new User();
        user.setName("Darwin Araque");
        user.setUserName("darwin.araque");
        user.setBirthDate(LocalDate.of(1993, Month.MAY, 4));
        return user;
    }

    @Test
    public void findAllTest() throws Exception{

        User userOne= getUser();
        User userTwo = getUser();

        List<User> users = Arrays.asList(userOne, userTwo);

        given(userRepository.findAll()).willReturn(users);

        mvc.perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void findOneTest() throws Exception {
        User user = getUser();
        given(userRepository.findById(1l)).willReturn(Optional.of(user));
        mvc.perform(get("/api/users/{id}", 1l))
            .andExpect(status().isOk());
    }

    @Test
    public void notFindOneTest() throws Exception {
        given(userRepository.findById(1l)).willReturn(Optional.of(getUser()));
        mvc.perform(get("/api/books/{id}", 1l))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteTest() throws Exception {
        given(user.getId()).willReturn(1l);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        mvc.perform(delete("/api/users/{id}", user.getId()))
            .andExpect(status().isOk());
    }

    @Test
    public void notFoundDeleteTest() throws Exception {
        mvc.perform(delete("/api/users/{id}", 2l))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void createTest() throws Exception {
        String userJson = "\n{\"username\":\"Darwin.Araque\",\"name\":\"Darwin\",\"birthDate\":\"2019-01-01\"}";
        mvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
            .andExpect(status().isCreated());
    }

    @Test
    public void notCreateTest() throws Exception {
        User user = getUser();
        mvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Utils.asJsonString(user)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateTest() throws Exception {
        String userJson = "\n{\"id\": 1,\"username\":\"Daraque\",\"name\":\"Daraque Ortiz\",\"birthDate\":\"2019-01-01\"}";
        given(userRepository.findById(1l)).willReturn(Optional.of(user));
        mvc.perform(put("/api/users/{id}", 1l)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
            .andExpect(status().isOk());
    }
}
