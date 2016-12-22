package resource;

public class Book {

    public String title;

    public Author author;

	public Boolean read;

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
		this.read = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

}
