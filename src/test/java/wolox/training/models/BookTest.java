package wolox.training.models;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

@AutoConfigureTestDatabase(replace= Replace.NONE)
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
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
    public void saveBook(){
        Book book = getBook();
        Book bookSaved = entityManager.persist(book);
        Optional<Book> bookInDb = bookRepository.findById(book.getId());
        assert (bookInDb.get()).equals(bookSaved);
    }

    @Test
    public void updateBook(){
        Book book = getBook();
        Book bookSaved = entityManager.persist(book);
        String newIsbn = "AV504311";
        bookSaved.setIsbn(newIsbn);
        entityManager.merge(bookSaved);
        Optional<Book> bookInDb = bookRepository.findById(book.getId());
        assert (newIsbn).equals(bookInDb.get().getIsbn());
    }

    @Test(expected = IllegalArgumentException.class)
    public void notCreateTest() {
        Book book = new Book();
        bookRepository.save(book);
    }
}
