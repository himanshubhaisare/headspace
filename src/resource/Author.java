package resource;

import java.util.ArrayList;
import java.util.List;

public class Author {

    public String name;

	public List<Book> books;

    public Author(String name) {
        this.name = name;
		this.books = new ArrayList<Book>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}
}
