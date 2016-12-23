package tests;

import constants.Error;
import database.Database;
import resource.Author;
import resource.Book;
import service.Library;

import java.util.HashMap;

public class LibraryTest {

    private static final String CREATE_USER_WITH_LONGER_NAME = "test cannot create user with a name longer than 15 chars";
    private static final String CREATE_USER_WITHOUT_NAME = "test cannot create user without user name";
    private static final String CREATE_USER_WITH_NAME = "test create user with user name";
    private static final String CREATE_USER_WITH_SHORTER_NAME = "test user cannot have name shorter than 4 chars";
    private static final String CREATE_USER_WITH_UNDERSCORE_NAME = "test user can have underscores name";
    private static final String CREATE_USER_WITH_DASH_NAME = "test user can have dashes in name";
    private static final String CREATE_USER_WITH_ALPHANUM_NAME = "test user can have alphanumeric name";
    private static final String BAD_COMMAND = "test bad command is not recognized";
    private static final String ADD_CARD_WITHOUT_USER = "test cannot add card without providing user name";
    private static final String ADD_CARD_ON_NON_EXISTING_USER = "test cannot add card on a user that does not exist";
    private static final String ADD_CARD_WITH_USER = "test user can add a card";
    private static final String ADD_NON_LUHN_CARD = "test cannot add a card that's non Luhn compatible";
    private static final String ADD_SECOND_CARD = "test cannot add second card on a user";
    private static final String ADD_ANOTHER_USERS_CARD = "test cannot add a card that belongs to another user";
    private static final String CHARGE_ON_CARD_AFTER_PAYMENT = "test that actor's card is charged after payment";
    private static final String CHECK_BALANCE_WITHOUT_USER = "test cannot check check balance without providing user name";
    private static final String STARTING_BALANCE_ON_USER = "test starting balance on a user is always $0";
    private static final String CHECK_BALANCE_USER_NOT_FOUND = "test cannot check balance on a user that does not exist";
    private static final String CHECK_BALANCE_AFTER_MAKING_PAYMENT = "test that balance does not decrease after making payment";
    private static final String CHECK_BALANCE_AFTER_RECEIVING_PAYMENT = "test that balance increases after receiving payment";
    private static final String MAKE_PAYMENT_WITHOUT_USER = "test cannot make payment without providing any arguments";
    private static final String MAKE_PAYMENT_WITHOUT_NOTES = "test cannot make payment without providing notes";
    private static final String MAKE_PAYMENT_TO_SELF = "test cannot make payment to yourself";
    private static final String MAKE_PAYMENT_WITHOUT_CARD = "test actor cannot make payment without card";
    private static final String MAKE_PAYMENT = "test actor can make payment to target";
    private static final String SHOW_FEED_WITHOUT_ARGS = "test cannot show feed without any arguments";
    private static final String SHOW_FEED_FOR_NON_EXISTING_USER = "test cannot show feed for a user that does not exist";
    private static final String SHOW_FEED = "test show feed works";

    private Library library;

    public LibraryTest(Library library) {
        this.library = library;
    }

    private void clear() {
        Database.setAuthors(new HashMap<String, Author>());
        Database.setBooks(new HashMap<String, Book>());
    }

    /**
     * All test cases
     *
     */
    public void run() {
        // 0. bad command
        testBadCommand();

        // 1. user will create a new user
        testCreateUserWithoutName();
        testCreateUserWithName();
        testCreateUserWithUnderscoreName();
        testCreateUserWithDashName();
        testCreateUserWithAlphanumericName();

        // 2. add adds a card on user
        testAddCardWithoutUser();
        testAddCardOnNonExistingUser();
        testAddCardWithUser();

        // 3. balance shows user's balance
        testBalanceWithoutUser();
        testBalanceOnNonExistingUser();
        testStartingBalanceOnUser();
        testBalanceAfterMakingPayment();
        testBalanceAfterReceivingPayment();

        // 4. pay makes a payment from actor to target
        testPaymentWithoutArgs();
        testPaymentWithoutNotes();
        testPaymentWithoutCard();

        // 5. feed shows activity feed of the user
        testFeedWithoutArgs();
        testFeedWithoutUser();
        testFeedForUser();
    }

    private void testFeedForUser() {
        library.handle("user Himanshu");
        library.handle("add Himanshu 5454545454545454");
        library.handle("user Milana");
        library.handle("user John");
        library.handle("user Newton");
        library.handle("pay Himanshu Milana $3.14 pie");
        library.handle("pay Himanshu John $3.14 pie");
        library.handle("pay Himanshu Newton $9.81 gravity");
        String result = library.handle("feed Himanshu");
        if (result.contains("You paid Milana $3.14 for pie") &&
            result.contains("You paid John $3.14 for pie") &&
            result.contains("You paid Newton $9.81 for gravity")) {
            System.out.println(SHOW_FEED+" : PASS");
        } else {
            System.out.println(SHOW_FEED+" : FAIL");
        }

        clear();
    }

    private void testFeedWithoutUser() {
        String result = library.handle("feed Himanshu");
        if (result.equals(Error.AUTHOR_NOT_FOUND)) {
            System.out.println(SHOW_FEED_FOR_NON_EXISTING_USER+" : PASS");
        } else {
            System.out.println(SHOW_FEED_FOR_NON_EXISTING_USER+" : FAIL");
        }
    }

    private void testFeedWithoutArgs() {
        String result = library.handle("feed");
        if (result.equals(Error.INVALID_ARGS)) {
            System.out.println(SHOW_FEED_WITHOUT_ARGS+" : PASS");
        } else {
            System.out.println(SHOW_FEED_WITHOUT_ARGS+" : FAIL");
        }
    }

    private void testPaymentWithoutCard() {
        library.handle("user Himanshu");
        library.handle("user Milana");
        String result = library.handle("pay Himanshu Milana $10.50 for breaking glass jar");
        if (result.equals(Error.TITLE_NOT_FOUND)) {
            System.out.println(MAKE_PAYMENT_WITHOUT_CARD+" : PASS");
        } else {
            System.out.println(MAKE_PAYMENT_WITHOUT_CARD+" : FAIL");
        }

        clear();
    }

    private void testPaymentWithoutNotes() {
        library.handle("user Himanshu");
        library.handle("add Himanshu 5454545454545454");
        library.handle("user Milana");
        library.handle("add Milana 4111111111111111");
        String result = library.handle("pay Himanshu Milana $10.50");
        if (result.equals(Error.INVALID_ARGS)) {
            System.out.println(MAKE_PAYMENT_WITHOUT_NOTES+" : PASS");
        } else {
            System.out.println(MAKE_PAYMENT_WITHOUT_NOTES+" : FAIL");
        }

        clear();
    }

    private void testPaymentWithoutArgs() {
        String result = library.handle("pay");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(MAKE_PAYMENT_WITHOUT_USER+" : PASS");
        } else {
            System.out.println(MAKE_PAYMENT_WITHOUT_USER+" : FAIL");
        }
    }

    private void testBalanceAfterReceivingPayment() {
        library.handle("user Himanshu");
        library.handle("add Himanshu 5454545454545454");
        library.handle("user Milana");
        library.handle("add Milana 4111111111111111");
        library.handle("pay Himanshu Milana $10.50 for not doing dishes");
        String result = library.handle("balance Milana");
        if (result.equals("$10.50")) {
            System.out.println(CHECK_BALANCE_AFTER_RECEIVING_PAYMENT+" Milana "+result+" : PASS");
        } else {
            System.out.println(CHECK_BALANCE_AFTER_RECEIVING_PAYMENT+" Milana "+result+" : FAIL");
        }

        clear();
    }

    private void testBalanceAfterMakingPayment() {
        library.handle("user Himanshu");
        library.handle("add Himanshu 5454545454545454");
        library.handle("user Milana");
        library.handle("add Milana 4111111111111111");
        library.handle("pay Himanshu Milana $10.50 for not doing dishes");
        String result = library.handle("balance Himanshu");
        if (result.equals("$0")) {
            System.out.println(CHECK_BALANCE_AFTER_MAKING_PAYMENT+" Himanshu "+result+" : PASS");
        } else {
            System.out.println(CHECK_BALANCE_AFTER_MAKING_PAYMENT+" Himanshu "+result+" : FAIL");
        }

        clear();
    }

    private void testStartingBalanceOnUser() {
        library.handle("user Himanshu");
        String balance = library.handle("balance Himanshu");
        if (balance.equals("$0")) {
            System.out.println(STARTING_BALANCE_ON_USER+" Himanshu "+balance+" : PASS");
        } else {
            System.out.println(STARTING_BALANCE_ON_USER+" Himanshu "+balance+" : FAIL");
        }

        clear();
    }

    private void testBalanceOnNonExistingUser() {
        String result = library.handle("balance Himanshu");
        if (result.contains(Error.AUTHOR_NOT_FOUND)) {
            System.out.println(CHECK_BALANCE_USER_NOT_FOUND+" Himanshu : PASS");
        } else {
            System.out.println(CHECK_BALANCE_USER_NOT_FOUND+" Himanshu : FAIL");
        }
    }

    private void testBalanceWithoutUser() {
        String result = library.handle("balance");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(CHECK_BALANCE_WITHOUT_USER+" : PASS");
        } else {
            System.out.println(CHECK_BALANCE_WITHOUT_USER+" : FAIL");
        }
    }

    private void testAddCardWithUser() {
        library.handle("user Himanshu");
        library.handle("add Himanshu 5454545454545454");
        Book book = Database.getBook("5454545454545454");
        if (book != null) {
            System.out.println(ADD_CARD_WITH_USER+" Himanshu 5454545454545454 : PASS");
        } else {
            System.out.println(ADD_CARD_WITH_USER+" Himanshu 5454545454545454 : FAIL");
        }

        clear();
    }

    private void testAddCardOnNonExistingUser() {
        String result = library.handle("add Himanshu 5454545454545454");
        if (result.contains(Error.AUTHOR_NOT_FOUND)) {
            System.out.println(ADD_CARD_ON_NON_EXISTING_USER+" Himanshu : PASS");
        } else {
            System.out.println(ADD_CARD_ON_NON_EXISTING_USER+" Himanshu : FAIL");
        }
    }

    private void testAddCardWithoutUser() {
        String result = library.handle("add");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(ADD_CARD_WITHOUT_USER+" : PASS");
        } else {
            System.out.println(ADD_CARD_WITHOUT_USER+" : FAIL");
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

    private void testCreateUserWithoutName() {
        String result = library.handle("user");
        if (result.contains(Error.INVALID_ARGS)) {
            System.out.println(CREATE_USER_WITHOUT_NAME+" : PASS");
        } else {
            System.out.println(CREATE_USER_WITHOUT_NAME+" : FAIL");
        }
    }

    private void testCreateUserWithName() {
        library.handle("user Himanshu");
        Author author = Database.getAuthor("Himanshu");
        if (author != null) {
            System.out.println(CREATE_USER_WITH_NAME+" Himanshu : PASS");
        } else {
            System.out.println(CREATE_USER_WITH_NAME+" Himanshu : FAIL");
        }

        // clear database
        clear();
    }

    private void testCreateUserWithUnderscoreName() {
        library.handle("user Him_an_shu");
        Author author = Database.getAuthor("Him_an_shu");
        if (author != null) {
            System.out.println(CREATE_USER_WITH_UNDERSCORE_NAME+" Him_an_shu : PASS");
        } else {
            System.out.println(CREATE_USER_WITH_UNDERSCORE_NAME+" Him_an_shu : FAIL");
        }

        // clear database
        clear();
    }

    private void testCreateUserWithDashName() {
        library.handle("user Him-ans-shu");
        Author author = Database.getAuthor("Him-ans-shu");
        if (author != null) {
            System.out.println(CREATE_USER_WITH_DASH_NAME+" Him-ans-shu : PASS");
        } else {
            System.out.println(CREATE_USER_WITH_DASH_NAME+" Him-ans-shu : FAIL");
        }

        // clear database
        clear();
    }

    private void testCreateUserWithAlphanumericName() {
        library.handle("user H1m8n3hu");
        Author author = Database.getAuthor("H1m8n3hu");
        if (author != null) {
            System.out.println(CREATE_USER_WITH_ALPHANUM_NAME+" H1m8n3hu : PASS");
        } else {
            System.out.println(CREATE_USER_WITH_ALPHANUM_NAME+" H1m8n3hu : FAIL");
        }

        // clear database
        clear();
    }
}
