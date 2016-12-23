package service;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;

import java.util.List;
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
        if (args.length < 2 || args[0].isEmpty() || args[1].isEmpty()) {
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

		Database.lastCommand.setAuthor(author);
		Database.lastCommand.setBook(book);

		result = String.format("\nAdded \"%s\" by %s\n\n> ", book.getTitle(), author.getName());
        return result;
    }

	/**
	 * Retrieve all books from library
	 *
	 * @return
	 */
	public String retrieve() {
		String result = "";
		Map<String, Book> books = Database.getBooks();

		result = books.entrySet().stream()
				.map(book -> String.format("\"%s\" by %s (%s)\n",
						book.getValue().getTitle(),
						book.getValue().getAuthor().getName(),
						book.getValue().getStatus()))
				.reduce(String::concat).orElseGet(() -> "");

		if (result.length() == 0) {
			result = "No books found\n";
		}

		result = "\n" + result + "\n> ";
		return result;
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
		if (book == null) {
			result = Error.TITLE_NOT_FOUND;
			return result;
		}

		book.setRead(true);
		Database.addBook(book);
		Database.lastCommand.setBook(book);
		Database.lastCommand.setAuthor(book.getAuthor());

		result = String.format("\n\"%s\" by %s marked as read\n\n> ", book.getTitle(), book.getAuthor().getName());
		return result;
	}

	/**
	 * Delete book
	 *
	 * @param book
	 * @return
	 */
	public String delete(Book book) {
		String result = "";
		if (book == null) {
			result = Error.TITLE_NOT_FOUND;
			return result;
		}

		Author author = book.getAuthor();
		Database.deleteBook(book);
		List<Book> books = author.getBooks();
		books.remove(book);
		if (books.isEmpty()) {
			Database.deleteAuthor(author);
		}

		result = String.format("\nRemoved \"%s\" by %s\n\n> ", book.getTitle(), author.getName());
		return result;
	}

	/**
	 * Mark book as unread
	 *
	 * @param book
	 * @return
	 */
	public String unread(Book book) {
		String result = "";
		if (book == null) {
			result = Error.TITLE_NOT_FOUND;
			return result;
		}

		book.setRead(false);
		Database.addBook(book);

		result = String.format("\n\"%s\" by %s marked as unread\n\n> ", book.getTitle(), book.getAuthor().getName());
		return result;
	}

	/**
	 * Retrieve all unread books
	 *
	 * @return
	 */
	public String retrieveUnreadBooks() {
		String result = "";
		Map<String, Book> books = Database.getBooks();

		result = books.entrySet().stream()
				.filter(book -> book.getValue().isUnread())
				.map(book ->
						String.format("\"%s\" by %s (%s)\n",
								book.getValue().getTitle(),
								book.getValue().getAuthor().getName(),
								book.getValue().getStatus()))
				.reduce(String::concat).orElseGet(() -> "");

		if (result.length() == 0) {
			result = String.format("No unread books found\n");
		}

		result = "\n" + result + "\n> ";
		return result;
	}
}
