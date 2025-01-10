package model;

public class BookAuthor {

	private int idBook;
    private int idAuthor;

    public BookAuthor(int idBook, int idAuthor) {
        this.idBook = idBook;
        this.idAuthor = idAuthor;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }
	
}
