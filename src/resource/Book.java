package resource;

public class Book {

    private String title;

    private Author author;

	private Boolean read;

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

	public Boolean isUnread() {
		return !read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public String getStatus() {
		String status = "unread";
		if (this.read) {
			return "read";
		}
		return status;
	}

}
