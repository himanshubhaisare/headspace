package service;

import constants.Error;
import database.Database;
import resource.Book;
import resource.Author;
import validator.Luhn;

public class BookService {

    public BookService() {
    }

    /**
     * Create card
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
        book = Database.getBook(args[1]);
        if (author == null) {
            result = Error.USER_NOT_FOUND;
            return result;
        }

        if (book == null) {
            if (author.getBooks() == null) {
                if (Luhn.validate(args[1])) {
                    book = new Book(args[1], author);
                    Database.setBook(book);
                    author.addBook(book);
                    Database.setAuthor(author);
                } else {
                    result = Error.CARD_NUMBER_INVALID;
                }
            } else {
                result = Error.USER_ALREADY_HAS_CARD;
            }
        } else {
            result = Error.CARD_BELONGS_TO_ANOTHER_USER;
        }

        return result;
    }
}
