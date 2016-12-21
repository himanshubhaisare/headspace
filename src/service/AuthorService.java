package service;

import constants.Error;
import database.Database;
import resource.Author;
import validator.Username;

import java.math.BigDecimal;
import java.util.Arrays;

public class AuthorService {

    /**
     * Validate and create a user
     *
     * @param args
     * @return
     */
    public String create(String[] args) {
        Author author;
        String result = "";
        if (args.length < 1) {
            result = Error.INVALID_ARGS;
            return result;
        }

        String name = Utils.concatenate(Arrays.copyOfRange(args, 0, args.length));
        if (Username.validate(name)) {
            author = new Author(name);
            Database.setAuthor(author);
        } else {
            result = Error.USERNAME_INVALID;
        }

        return result;
    }

    /**
     * Show user's balance
     *
     * @param args
     * @return
     */
    public String showBalance(String[] args) {
        String result;
        BigDecimal balance;
        if (args.length < 1) {
            result = Error.INVALID_ARGS;
            return result;
        }

        Author author = Database.getAuthor(args[0]);
        if (author == null) {
            result = Error.USER_NOT_FOUND;
        } else {
            balance = author.getBalance();
            result = "$"+balance.toString();
        }

        return result;
    }
}
