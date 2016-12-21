package resource;

import java.math.BigDecimal;

public class Book {

    public String number;

    public Author author;

    public BigDecimal charge;

    public Book(String number, Author author) {
        this.number = number;
        this.author = author;
        this.charge = BigDecimal.ZERO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }
}
