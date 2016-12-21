package service;

import constants.Command;

import java.util.Arrays;

import static constants.Error.*;

public class Library {

    private AuthorService authorService;

    private BookService bookService;

    /**
     * Library Application runs with user, card and payment services
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
        String result;
        String[] inputs = input.split(" ");
        if (inputs.length < 1) {
            result = INVALID_ARGS;
        } else {
            String command = inputs[0];
            String[] args = Arrays.copyOfRange(inputs, 1, inputs.length);
            if (command == null) {
                result = COMMAND_NOT_RECOGNIZED;
            } else {
                switch (command) {
                    case Command.ADD:
                        result = this.bookService.create(args);
                        break;
                    case Command.BALANCE:
                        result = this.authorService.showBalance(args);
						result += "\n";
                        break;
                    case Command.USER:
                        result = this.authorService.create(args);
                        break;
                    default:
                        result = COMMAND_NOT_RECOGNIZED;
                        break;
                }
            }
        }

		if(result.equals(COMMAND_NOT_RECOGNIZED) || result.equals(INVALID_ARGS) ||
				result.equals(USER_ALREADY_HAS_CARD) || result.equals(CARD_BELONGS_TO_ANOTHER_USER) ||
				result.equals(USER_NOT_FOUND) || result.equals(ACTOR_NOT_FOUND) || result.equals(TARGET_NOT_FOUND) ||
				result.equals(CARD_NOT_FOUND) || result.equals(USERNAME_INVALID) || result.equals(CARD_NUMBER_INVALID) ||
				result.equals(CANNOT_PAY_SELF)) {
			result += "\n";
		}

        return result;
    }
}
