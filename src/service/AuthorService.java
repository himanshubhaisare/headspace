package service;

import constants.Error;
import database.Database;
import resource.Author;
import validator.Username;

import java.util.Arrays;

public class AuthorService {

    /**
     * Validate and create a Author
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
	 * Return all authors
	 *
	 * @param args
	 * @return
	 */
	public String retrieve(String[] args) {
		return null;
	}
}
