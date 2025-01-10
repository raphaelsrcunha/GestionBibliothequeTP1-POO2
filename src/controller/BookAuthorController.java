package controller;

import model.BookAuthor;
import model.BookAuthorDAO;

public class BookAuthorController {
	
	private BookAuthorDAO bookAuthorDAO;
	
	public BookAuthorController(BookAuthorDAO bookAuthorDAO) {
		this.bookAuthorDAO = bookAuthorDAO;
	}
	
	public void addBookAuthor(BookAuthor bookAuthor) {
		bookAuthorDAO.addBookAuthor(bookAuthor);
	}

}
