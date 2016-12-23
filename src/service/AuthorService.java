package service;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;

import java.util.List;

public class AuthorService {

	/**
	 * Retrieve all books by an author
	 *
	 * @param args
	 * @return
	 */
	public String retrieveBooks(String[] args) {
		String result = "";
		if (args.length < 1) {
			result = Error.INVALID_ARGS;
			return result;
		}

		Author author = Database.getAuthor(args[0]);
		if (author == null) {
			result = Error.AUTHOR_NOT_FOUND;
			return result;
		}

		List<Book> books = author.getBooks();
		result = books.stream()
				.map(book ->
						String.format("\"%s\" by %s (%s)\n",
								book.getTitle(),
								book.getAuthor().getName(),
								book.getStatus()))
				.reduce(String::concat).orElseGet(() -> "");

		if (result.length() == 0) {
			result = String.format("No books found by %s\n", author.getName());
		}

		result = "\n" + result + "\n> ";
		return result;
	}

	/**
	 * Retrieve all unread books by an author
	 *
	 * @param args
	 * @return
	 */
	public String retrieveUnreadBooks(String[] args) {
		String result = "";
		if (args.length < 1) {
			result = Error.INVALID_ARGS;
			return result;
		}

		Author author = Database.getAuthor(args[0]);
		if (author == null) {
			result = Error.AUTHOR_NOT_FOUND;
			return result;
		}

		List<Book> books = author.getBooks();
		result = books.stream()
				.filter(Book::isUnread)
				.map(book ->
						String.format("\"%s\" by %s (%s)\n",
								book.getTitle(),
								book.getAuthor().getName(),
								book.getStatus()))
				.reduce(String::concat).orElseGet(() -> "");

		if (result.length() == 0) {
			result = String.format("No unread books found by %s\n", author.getName());
		}

		result = "\n" + result + "\n> ";
		return result;
	}
}
