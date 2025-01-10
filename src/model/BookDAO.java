package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;

public class BookDAO {

	private DatabaseManager dbManager;
	
	public BookDAO() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public List<Book> getAllBooks() {
		
		List<Book> books = new ArrayList<>();
		String query = "SELECT * FROM livre";
		
		try {
			Connection connection = dbManager.getConnection();
			
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					Book book = new Book(
							resultSet.getInt("ID_Livre"),
							resultSet.getString("Titre"),
							resultSet.getInt("Annee_Publication"),
	                        resultSet.getString("ISBN"),
	                        resultSet.getInt("ID_Editeur"),
	                        resultSet.getInt("ID_Categorie")
							);
					books.add(book);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return books;
	}
	
	public int addBook(Book book) {
		
		String query = "INSERT INTO Livre (Titre, Annee_Publication, ISBN, ID_Editeur, ID_Categorie) VALUES (?, ?, ?, ?, ?)";
	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        statement.setString(1, book.getTitre());
	        statement.setInt(2, book.getAnnee_Publication());
	        statement.setString(3, book.getISBN());
	        statement.setInt(4, book.getID_Editeur());
	        statement.setInt(5, book.getID_Categorie());
	        statement.executeUpdate();

	        // Récupérer l'ID généré
	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1); // Retourne l'ID généré
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;
	}
	
	public void deleteBook(int id) {
		
		String query = "DELETE FROM livre WHERE ID_Livre = ?";
		
		try {
			Connection connection = dbManager.getConnection();
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, id);
				statement.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateBook(int id) {
		
	}
	
}
