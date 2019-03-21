package wolox.training.models;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.UserRepository;

@AutoConfigureTestDatabase(replace= Replace.NONE)
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User getUser(){
        User user = new User();
        user.setName("Darwin Araque");
        user.setUserName("darwin.araque");
        user.setBirthDate(LocalDate.of(1993, Month.MAY, 4));
        return user;
    }

    @Test
    public void saveUser() {
        User user = getUser();
        User userSaved = entityManager.persist(user);
        Optional<User> userInDB = userRepository.findById(userSaved.getId());
        assert (userInDB.get()).equals(userSaved);
    }

    @Test
    public void updateUser(){
        User user = getUser();
        User userSaved = entityManager.persist(user);
        String newName = "Ortiz";
        userSaved.setName(newName);
        entityManager.merge(userSaved);
        Optional<User> userInDB = userRepository.findById(userSaved.getId());
        assert (newName).equals(userInDB.get().getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void notCreateTest() {
        User user = new User();
        user.setUserName("Cristian Test");
        user.setName(null);
        user.setBirthDate(null);
        userRepository.save(user);
    }
}
