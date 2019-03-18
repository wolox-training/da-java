package wolox.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateBook(@RequestBody User user, @PathVariable Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException("Not found a user with id");
        }
        userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @PutMapping("/{user_id}/books/{book_id}")
    public void addBook(@PathVariable Long book_id, @PathVariable Long user_id) {
        User user = userRepository.findById(user_id)
            .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(book_id)
            .orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        userRepository.save(user);
    }

    @DeleteMapping("/{user_id}/books/{book_id}")
    public void deleteBook(@PathVariable Long user_id, @PathVariable Long book_id) {
        Book book = bookRepository.findById(book_id)
            .orElseThrow(BookNotFoundException::new);
        User user = userRepository.findById(user_id)
            .orElseThrow(UserNotFoundException::new);
            user.removeBook(book);
        userRepository.save(user);
    }


}
