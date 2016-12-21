import service.BookService;
import service.AuthorService;
import service.Library;
import tests.LibraryTest;

public class LibraryTestApplication {

    /**
     * Library tests
     *
     * @param args
     */
    public static void main(String[] args) {

        AuthorService authorService = new AuthorService();
        BookService bookService = new BookService();
        Library library = new Library(authorService, bookService);
        LibraryTest libraryTest = new LibraryTest(library);

        libraryTest.run();
    }
}
