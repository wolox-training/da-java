package wolox.training.models;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Book {

    public static String preconditionEmptyMessage = "for a book cant be null or empty";

    public Book(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        Preconditions.checkArgument(genre != null && !genre.isEmpty(),
            "The genre " + Book.preconditionEmptyMessage);
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(author != null && !author.isEmpty(),
            "The author " + Book.preconditionEmptyMessage);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkArgument(image != null && !image.isEmpty(),
            "The image " + Book.preconditionEmptyMessage);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument(genre != null && !genre.isEmpty(),
            "The title " + Book.preconditionEmptyMessage);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument(subtitle != null && !subtitle.isEmpty(),
            "The subtitle " + Book.preconditionEmptyMessage);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(publisher != null && !publisher.isEmpty(),
            "The publisher " + Book.preconditionEmptyMessage);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkArgument(year != null && !year.isEmpty(),
            "The year " + Book.preconditionEmptyMessage);
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        Preconditions.checkArgument(pages != null && pages > 0,
            "The pages " + Book.preconditionEmptyMessage);
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument(isbn != null && !isbn.isEmpty(),
            "The isbn " + Book.preconditionEmptyMessage);
        this.isbn = isbn;
    }

    public List<User> getUsers() {
        return (List<User>) Collections.unmodifiableList(users);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id;
    }
}
