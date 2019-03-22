package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User, Long > {

    User findByUserName(String userName);

    @Query("SELECT u FROM User u WHERE (lower(u.userName) like "
        + " lower(concat('%', :userName,'%'))) OR u.userName IS NULL "
        + "AND (u.birthDate >= :initDate) AND (u.birthDate <= :endDate) OR u.birthDate IS NULL")
    List<User> findByBirthDateUserName(@Param("initDate") LocalDate initDate,
        @Param("endDate") LocalDate endDate, @Param("userName") String userName);
}
