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
		System.out.println("Welcome to your library!\n");
		String input = "";
		while (!input.equals("close")) {
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();
			if (input.equals("help")) {
				System.out.println("user <name> : to create a user. e.g. user Himanshu \n" +
						"add <user> <card number> : to add a credit card on user. e.g. add Himanshu 5555555555554444 \n" +
						"balance <user> : to view balance of a user e.g. balance Himanshu \n" +
						"pay <actor> <target> <$amount> <note> : pay someone e.g. pay Himanshu Lisa $10.50 for coffee \n" +
						"feed <user> : shows activity feed of a user e.g. feed Himanshu \n" +
						"help : brings up manual \n" +
						"close : closes library \n");
			} else {
				String result = library.handle(input);
				System.out.print(result);
			}
		}
    }
}
