package database;

import resource.Author;
import resource.Book;

import java.util.HashMap;
import java.util.Map;

public class Database {

    static Map<String, Author> authors = new HashMap<String, Author>();

    static Map<String, Book> books = new HashMap<String, Book>();

    public static Map<String, Author> getAuthors() {
        return authors;
    }

    public static void setAuthors(Map<String, Author> authors) {
        Database.authors = authors;
    }

    public static Author getAuthor(String name) {
        return authors.get(name);
    }

    public static void addAuthor(Author author) {
        Database.authors.put(author.getName(), author);
    }

    public static Map<String, Book> getBooks() {
        return books;
    }

    public static Book getBook(String number) {
        return books.get(number);
    }

    public static void setBooks(Map<String, Book> books) {
        Database.books = books;
    }

    public static void addBook(Book book) {
        Database.books.put(book.getTitle(), book);
    }
}
