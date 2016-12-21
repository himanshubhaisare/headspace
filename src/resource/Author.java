package resource;

import java.math.BigDecimal;

public class Author {

    public String name;

    public BigDecimal balance;

    public Book book;

    public Author(String name) {
        this.name = name;
        this.balance = BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
