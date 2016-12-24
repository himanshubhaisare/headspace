package tests;

import constants.Error;
import database.Database;
import database.Last;
import resource.Author;
import resource.Book;
import service.Library;

import java.util.HashMap;

public class LibraryTest {

	private static final String ADD_WITHOUT_ARGS = "add command without arguments returns error";
	private static final String ADD_TITLE_WITH_EMPTY_AUTHORNAME = "add title with empty author name returns error";
    private static final String ADD_TITLE_WITH_AUTHOR = "add command with title and author name works";
	private static final String BAD_COMMAND = "test bad command is not recognized";
	private static final String CORRECT_COMMAND = "test correct command is recognized";
    private static final String READ_WITHOUT_ARGS = "read command without args returns error";
    private static final String READ_TITLE = "read title marks it read";
	private static final String SHOW_ALL_ON_EMPTY_LIBRARY = "show all command on empty library returns No books found";
	private static final String SHOW_ALL_SUCCESS = "show all returns all books in library";
    private static final String SHOW_UNREAD_ON_EMPTY_LIBRARY = "show unread on empty library returns No unread books found";
    private static final String SHOW_UNREAD = "show unread returns all unread titles";
    private static final String SHOW_ALL_BY_WITHOUT_ARGS = "show all by without arguments returns error";
    private static final String SHOW_ALL_BY_AUTHOR_WITH_NO_TITLES = "show all by author with no titles returns error";
    private static final String SHOW_ALL_BY_AUTHOR_WITH_TITLES = "show all by author name returns list of titles for that author";
	private static final String ADD_WITHOUT_AUTHOR = "add title without author returns error";
	private static final String SHOW_UNREAD_BY_WITHOUT_ARGS = "show unread by without args returns error";
	private static final String SHOW_UNREAD_BY_AUTHOR_WITH_READ_TITLES = "show unread by author with read titles doesn't return unread titles";
	private static final String SHOW_UNREAD_BY_AUTHOR_WITH_UNREAD_TITLES = "show unread by author with unread titles returns unread titles";
	private static final String UNDO_LAST_MUTATIONAL_COMMAND = "undo last add command removed the added title from library";
	private static final String UNDO_LAST_NON_MUTATIONAL_COMMAND = "undo last non mutational command like \"read\" returns error";
	private static final String QUIT_SAYS_BYE = "quit command says Bye! before ending the program";

	private Library library;

    public LibraryTest(Library library) {
        this.library = library;
    }

    private void clear() {
        Database.setAuthors(new HashMap<String, Author>());
        Database.setBooks(new HashMap<String, Book>());
		Database.lastCommand = new Last();
    }

    /**
     * All test cases
     *
     */
    public void run() {
        // 0. bad command
        testBadCommand();
		testCorrectCommand();

        // 1. test "add" command
        testAddWithoutArgs();
        testAddTitleWithoutAuthor();
        testAddTitleWithEmptyAuthorName();
        testAddWithTitleAndAuthor();

        // 2. test "read" command
        testReadWithoutArgs();
        testReadTitle();

        // 3. test "show all" command
        testShowAllOnEmptyLibrary();
        testShowAll();

        // 4. test "show unread" command
        testShowUnreadOnEmptyLibrary();
        testShowUnread();

        // 5. test "show all by" command
        testShowAllByWithoutArgs();
        testShowAllByAuthorWithNoTitles();
        testShowAllByAuthorWithTitles();

		// 6. test "show unread by" command
		testShowUnreadByWithoutArgs();
		testShowUnreadByAuthorWithReadTitles();
		testShowUnreadByAuthorWithUnreadTitles();

		// 7. test "undo" command
		testUndoMutationalLastCommand();
		testUndoNonMutationalLastCommand();

		// 8. test "quit" command
		testQuit();
    }

	private void testQuit() {
		String result = library.handle("quit");
		if (result.contains("Bye!")) {
			System.out.println(QUIT_SAYS_BYE +" : PASS");
		} else {
			System.out.println(QUIT_SAYS_BYE +" : FAIL");
		}

		clear();
	}

	private void testUndoNonMutationalLastCommand() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
		library.handle("show read");
		String result = library.undo();
		if (result.contains(Error.UNDO_FAILED)) {
			System.out.println(UNDO_LAST_NON_MUTATIONAL_COMMAND +" : PASS");
		} else {
			System.out.println(UNDO_LAST_NON_MUTATIONAL_COMMAND +" : FAIL");
		}

		clear();
	}

	private void testUndoMutationalLastCommand() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
		String result = library.undo();
		if (result.contains("Removed \"The Grapes of Wrath\" by John Steinbeck\n")) {
			System.out.println(UNDO_LAST_MUTATIONAL_COMMAND +" : PASS");
		} else {
			System.out.println(UNDO_LAST_MUTATIONAL_COMMAND +" : FAIL");
		}

		clear();
	}

	private void testShowUnreadByAuthorWithUnreadTitles() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
		String result = library.handle("show unread by \"John Steinbeck\"");
		if (result.contains("\"The Grapes of Wrath\" by John Steinbeck (unread)")) {
			System.out.println(SHOW_UNREAD_BY_AUTHOR_WITH_UNREAD_TITLES +" : PASS");
		} else {
			System.out.println(SHOW_UNREAD_BY_AUTHOR_WITH_UNREAD_TITLES +" : FAIL");
		}

		clear();
	}

	private void testShowUnreadByAuthorWithReadTitles() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
		library.handle("read \"The Grapes of Wrath\"");
		String result = library.handle("show unread by \"John Steinbeck\"");
		if (result.contains("No unread books found by \"John Steinbeck\"")) {
			System.out.println(SHOW_UNREAD_BY_AUTHOR_WITH_READ_TITLES +" : PASS");
		} else {
			System.out.println(SHOW_UNREAD_BY_AUTHOR_WITH_READ_TITLES +" : FAIL");
		}

		clear();
	}

	private void testShowUnreadByWithoutArgs() {
		String result = library.handle("show unread by");
		if (result.contains(Error.INVALID_ARGS)) {
			System.out.println(SHOW_UNREAD_BY_WITHOUT_ARGS +" : PASS");
		} else {
			System.out.println(SHOW_UNREAD_BY_WITHOUT_ARGS +" : FAIL");
		}

		clear();
	}

	private void testCorrectCommand() {
		String result = library.handle("help");
		if (result.contains("add \"$title\" \"$author\"")) {
			System.out.println(CORRECT_COMMAND+" help : PASS");
		} else {
			System.out.println(CORRECT_COMMAND+" help : FAIL");
		}
	}

	private void testShowAllByAuthorWithTitles() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
        String result = library.handle("show all by \"John Steinbeck\"");
        if (result.contains("\"The Grapes of Wrath\" by John Steinbeck (unread)")) {
            System.out.println(SHOW_ALL_BY_AUTHOR_WITH_TITLES +" : PASS");
        } else {
            System.out.println(SHOW_ALL_BY_AUTHOR_WITH_TITLES +" : FAIL");
        }

        clear();
    }

    private void testShowAllByAuthorWithNoTitles() {
        String result = library.handle("show all by \"Himanshu Bhaisare\"");
        if (result.contains(Error.AUTHOR_NOT_FOUND)) {
            System.out.println(SHOW_ALL_BY_AUTHOR_WITH_NO_TITLES +" : PASS");
        } else {
            System.out.println(SHOW_ALL_BY_AUTHOR_WITH_NO_TITLES +" : FAIL");
        }
    }

    private void testShowAllByWithoutArgs() {
        String result = library.handle("show all by");
        if (result.equals(Error.INVALID_ARGS)) {
            System.out.println(SHOW_ALL_BY_WITHOUT_ARGS +" : PASS");
        } else {
            System.out.println(SHOW_ALL_BY_WITHOUT_ARGS +" : FAIL");
        }
    }

    private void testShowUnread() {
        library.handle("add \"Moby Dick\" \"Herman Melville\"");
        String result = library.handle("show unread");
        if (result.contains("unread")) {
            System.out.println(SHOW_UNREAD +" : PASS");
        } else {
            System.out.println(SHOW_UNREAD +" : FAIL");
        }

        clear();
    }

    private void testShowUnreadOnEmptyLibrary() {
        String result = library.handle("show unread");
        if (result.contains("No unread books found")) {
            System.out.println(SHOW_UNREAD_ON_EMPTY_LIBRARY +" : PASS");
        } else {
            System.out.println(SHOW_UNREAD_ON_EMPTY_LIBRARY +" : FAIL");
        }
    }

    private void testShowAll() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
        String result = library.handle("show all");
        if (result.contains("\"The Grapes of Wrath\" by John Steinbeck (unread)")) {
            System.out.println(SHOW_ALL_SUCCESS +" : PASS");
        } else {
            System.out.println(SHOW_ALL_SUCCESS +" : FAIL");
        }

        clear();
    }

    private void testShowAllOnEmptyLibrary() {
        String result = library.handle("show all");
        if (result.contains("No books found")) {
            System.out.println(SHOW_ALL_ON_EMPTY_LIBRARY +" : PASS");
        } else {
            System.out.println(SHOW_ALL_ON_EMPTY_LIBRARY +" : FAIL");
        }
    }

    private void testReadTitle() {
		library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
        String result = library.handle("read \"The Grapes of Wrath\"");
        if (result.contains("\"The Grapes of Wrath\" by John Steinbeck marked as read\n")) {
            System.out.println(READ_TITLE +" : PASS");
        } else {
            System.out.println(READ_TITLE +" : FAIL");
        }

        clear();
    }

    private void testReadWithoutArgs() {
        String result = library.handle("read");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(READ_WITHOUT_ARGS +" : PASS");
        } else {
            System.out.println(READ_WITHOUT_ARGS +" : FAIL");
        }
    }

    private void testBadCommand() {
        String result = library.handle("foobar");
        if (result.contains(Error.COMMAND_NOT_RECOGNIZED)) {
            System.out.println(BAD_COMMAND+" foobar : PASS");
        } else {
            System.out.println(BAD_COMMAND+" foobar : FAIL");
        }
    }

    private void testAddWithoutArgs() {
        String result = library.handle("add");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(ADD_WITHOUT_ARGS +" : PASS");
        } else {
            System.out.println(ADD_WITHOUT_ARGS +" : FAIL");
        }
    }

    private void testAddTitleWithoutAuthor() {
        String result = library.handle("add \"The Grapes of Wrath\"");
		if (result.contains(Error.INVALID_ARGS)) {
			System.out.println(ADD_WITHOUT_AUTHOR +" : PASS");
		} else {
			System.out.println(ADD_WITHOUT_AUTHOR +" : FAIL");
		}

        // clear database
        clear();
    }

    private void testAddTitleWithEmptyAuthorName() {
        String result = library.handle("add \"The Grapes of Wrath\" \"\"");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(ADD_TITLE_WITH_EMPTY_AUTHORNAME+" : PASS");
        } else {
            System.out.println(ADD_TITLE_WITH_EMPTY_AUTHORNAME+" : FAIL");
        }

        // clear database
        clear();
    }

    private void testAddWithTitleAndAuthor() {
        String result = library.handle("add \"The Grapes of Wrath\" \"John Steinbeck\"");
        if (result.contains("Added \"The Grapes of Wrath\" by John Steinbeck")) {
            System.out.println(ADD_TITLE_WITH_AUTHOR +" : PASS");
        } else {
            System.out.println(ADD_TITLE_WITH_AUTHOR +" : FAIL");
        }

        // clear database
        clear();
    }
}
