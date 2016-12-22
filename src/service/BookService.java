package service;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;

import java.util.HashMap;
import java.util.Map;

public class BookService {

    public BookService() {
    }

    /**
     * Add book in the library
     *
     * @param args
     * @return
     */
    public String create(String[] args) {
        Book book;
        String result = "";
        if (args.length < 2) {
            result = Error.INVALID_ARGS;
            return result;
        }

        Author author = Database.getAuthor(args[1]);
		if (author == null) {
			author = new Author(args[1]);
		}

		book = new Book(args[0], author);
		author.addBook(book);

		Database.addBook(book);
		Database.addAuthor(author);

		result = String.format("\nAdded \"%s\" by %s\n\n> ", book.getTitle(), author.getName());
        return result;
    }

	/**
	 * Retrieve books from library
	 *
	 * @param args
	 * @return
	 */
	public String retrieve(String[] args) {
		String result = "";
		Map<String, Book> books = new HashMap<>();
		if (args.length == 0) {
			books = Database.getBooks();
		}

		result = (books.entrySet().stream().map(entry -> {
			Book book = entry.getValue();
			return String.format("\"%s\" by %s (%s)\n", book.getTitle(), book.getAuthor().getName(), book.getStatus());
		})).reduce(String::concat).get();

		result = "\n" + result + "\n> ";
		return result;
	}

	public String retrieveOne(String[] args) {
		return null;
	}

	/**
	 * Mark a book as read
	 *
	 * @param args
	 * @return
	 */
	public String read(String[] args) {
		String result = "";
		if (args.length < 1) {
			result = Error.INVALID_ARGS;
			return result;
		}

		Book book = Database.getBook(args[0]);
		book.setRead(true);
		Database.addBook(book);

		result = String.format("\n\"%s\" by %s marked as read\n\n> ", book.getTitle(), book.getAuthor().getName());
		return result;
	}
}
