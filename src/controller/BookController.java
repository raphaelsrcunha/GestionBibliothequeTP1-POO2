package controller;

import java.util.List;

import model.Book;
import model.BookDAO;

public class BookController {

	private BookDAO bookDAO;

    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    public int addBook(Book book) {
		return bookDAO.addBook(book);
	}
    
    public void deleteBook(int id) {
    	bookDAO.deleteBook(id);
    }
    
    public void updateBook(int id) {
    	
    }
}
