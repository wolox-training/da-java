package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;
import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User, Long > {

    User findByUserName(String userName);
}
