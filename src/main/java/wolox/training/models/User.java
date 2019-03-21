package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@Table(name="users")
public class User {

    public User(){

    }

    public static String preconditionEmptyMessage = "for an user cant be null or empty";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Book> books = new HashSet();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkArgument(name != null && !name.isEmpty(),
            "The name for an user " + User.preconditionEmptyMessage);
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        Preconditions.checkArgument(userName != null && !userName.isEmpty(),
            "The username for an user " + User.preconditionEmptyMessage);
        this.userName = userName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        Preconditions.checkNotNull(birthDate,
            "The birth date for an user cant be null");
        this.birthDate = birthDate;
    }

    public Set<Book> getBooks() {
        return (Set<Book>) Collections.unmodifiableSet(books);
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
       if (!this.books.add(book)){
           Exception core = new RuntimeException("Unique constrains violated for book_user");
           throw new BookAlreadyOwnedException("Book already relationated to the user", core);
       }
    }

    public boolean removeBook(Book book) {
        return this.books.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }
}
