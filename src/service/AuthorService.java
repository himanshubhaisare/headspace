package service;

import constants.Error;
import database.Database;
import resource.Author;

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
		author = new Author(name);
		Database.addAuthor(author);


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
