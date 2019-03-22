package wolox.training.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book, Long > {

    Book findByAuthor( String author );

    Book findByIsbn ( String isbn);

    @Query("SELECT b FROM Book b WHERE (b.publisher  = "
        + ":publisher OR b.publisher IS NULL) AND (b.genre = :genre OR b.genre IS NULL) AND "
        + "(b.year = :year OR b.year IS NULL)")
    List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher,
        @Param("genre") String genre, @Param("year") String year);
}
