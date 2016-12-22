package service;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;

import java.util.Map;
import java.util.TreeMap;

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
		Map<String, Book> books = new TreeMap<String, Book>();
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
}
