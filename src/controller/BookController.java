package controller;

import java.util.List;

import model.Book;
import model.BookDAO;
import model.BookDTO;

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
    
    public void updateBook(Book book) {
    	bookDAO.updateBook(book);
    }
    
    public boolean isBookAvailable(int bookId) {
        return bookDAO.isBookAvailable(bookId);
    }
    
    public Book getBook(int id) {
    	return bookDAO.getBook(id);
    }
    
    public List<Book> searchByTitle(String title) {
    	return bookDAO.searchByTitle(title);
    }

    public Book getBookByTitle(String title) {
        return bookDAO.getBookByTitle(title);
    }
    
    public List<BookDTO> getAllBooksWithDetails() {
    	return bookDAO.getAllBooksWithDetails();
    }
    
    public BookDTO getBookDetails(int bookId) {
        return bookDAO.getBookDetailsById(bookId);
    }
    
}
