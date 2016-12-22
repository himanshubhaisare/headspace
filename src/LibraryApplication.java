import constants.Command;
import service.AuthorService;
import service.BookService;
import service.Library;

import java.util.Scanner;

public class LibraryApplication {

    /**
     * Library application runner
     *
     * @param args
     */
    public static void main(String[] args) {
        AuthorService authorService = new AuthorService();
        BookService bookService = new BookService();
        Library library = new Library(authorService, bookService);
		System.out.print("Welcome to your library!\n\n> ");
		String input = "";
		while (!input.equals(Command.QUIT)) {
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();
			String result = library.handle(input);
			System.out.print(result);
		}
    }
}
