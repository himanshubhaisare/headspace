package service;

import constants.Command;

import java.util.Arrays;

import static constants.Error.*;

public class Library {

    private AuthorService authorService;

    private BookService bookService;

    /**
     * Library Application runs with Author and Book services
	 *
     * @param authorService
     * @param bookService
	 */
    public Library(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    /**
     * Parse and execute the input commands
     *
     * @param input
     */
    public String handle(String input) {
        String result = "";
        String[] inputs = input.split(" ");
        if (inputs.length < 1) {
            result = INVALID_ARGS;
        } else {
            String command = inputs[0];
            String[] args = Arrays.copyOfRange(inputs, 1, inputs.length);
            if (command == null) {
                result = COMMAND_NOT_RECOGNIZED;
				result += "\n";
            } else {
                switch (command) {
					case Command.HELP:
						result = "add \"$title\" \"$author\": adds a books to the library with the given title and author. All books are unread by default.\n" +
								"read \"$title\": marks a given books as read.\n" +
								"show all: displays all of the books in the library\n" +
								"show unread: display all of the books that are unread\n" +
								"show all by \"$author\": shows all of the books in the library by the given author.\n" +
								"show unread by \"$author\": shows the unread books in the library by the given author\n" +
								"undo: undoes the last mutational command (if a books was marked as read it marks it as unread; if a books was added, it gets removed)\n" +
								"quit: quits the program.\n\n> ";
						break;
					case Command.QUIT:
						result = "\nBye!\n";
						break;
                    case Command.ADD:
                        result = this.bookService.create(args);
                        break;
                    case Command.SHOW_ALL:
                        result = this.authorService.retrieve(args);
						result += "\n> ";
                        break;
                    case Command.SHOW_ALL_BY:
                        result = this.authorService.create(args);
                        break;
					case Command.SHOW_UNREAD:
						result = this.authorService.create(args);
						break;
					case Command.SHOW_UNREAD_BY:
						result = this.authorService.create(args);
						break;
					case Command.READ:
						result = this.authorService.create(args);
						break;
					case Command.UNDO:
						result = this.authorService.create(args);
						break;
                    default:
                        result = COMMAND_NOT_RECOGNIZED;
						result += "\n> ";
                        break;
                }
            }
        }

        return result;
    }
}
