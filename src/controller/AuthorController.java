package controller;

import java.util.List;

import model.Author;
import model.AuthorDAO;

public class AuthorController {

	private AuthorDAO authorDAO;
	
	public AuthorController(AuthorDAO authorDAO) {
		this.authorDAO = authorDAO;
	}
	
	public List<Author> getAllAuthors() {
		return authorDAO.getAllAuthors();
	}
	
	public int addAuthor(Author author) {
		return authorDAO.addAuthor(author);
	}
	
	public void deleteAuthor(int id) {
		authorDAO.deleteAuthor(id);
	}
	
	public void updateAuthor(Author author) {
		authorDAO.updateAuthor(author);
	}
}
