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
		System.out.println("Welcome to your library!\n\n> ");
		String input = "";
		while (!input.equals("quit")) {
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();
			if (input.equals("help")) {
				System.out.println(
						"add \"$title\" \"$author\": adds a book to the library with the given title and author. All books are unread by default.\n" +
						"read \"$title\": marks a given book as read.\n" +
						"show all: displays all of the books in the library\n" +
						"show unread: display all of the books that are unread\n" +
						"show all by \"$author\": shows all of the books in the library by the given author.\n" +
						"show unread by \"$author\": shows the unread books in the library by the given author\n" +
						"undo: undoes the last mutational command (if a book was marked as read it marks it as unread; if a book was added, it gets removed)\n" +
						"quit: quits the program.");
			} else {
				String result = library.handle(input);
				System.out.print(result);
			}
		}
    }
}
