package service;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;

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

        Author author = Database.getAuthor(args[0]);
		if (author == null) {
			author = new Author(args[0]);
		}

		book = new Book(args[1], author);
		author.addBook(book);

		Database.addBook(book);
		Database.addAuthor(author);

        return result;
    }
}
