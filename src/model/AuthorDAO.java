package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;

public class AuthorDAO {

	private DatabaseManager dbManager;
	
	public AuthorDAO() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public List<Author> getAllAuthors() {
		
		List<Author> authors = new ArrayList<>();
		String query = "SELECT * FROM auteur";
		
		try {
			Connection connection = dbManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
				while(resultSet.next()) {
					Author author = new Author(
							resultSet.getInt("ID_Auteur"),
							resultSet.getString("Nom"),
							resultSet.getString("Prenom"),
							resultSet.getDate("Date_Naissance")
							);
					authors.add(author);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authors;
	}
	
	public int addAuthor(Author author) {
		String query = "INSERT INTO auteur (Nom, Prenom, Date_Naissance) VALUES (?, ?, ?)";
		
		try(Connection connection = dbManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, author.getNom());
			statement.setString(2, author.getPrenom());
			statement.setDate(3, new java.sql.Date(author.getDateNaissance().getTime()));
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1); // Retourne l'ID généré
	            }
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteAuthor(int id) {
		String query = "DELETE FROM auteur WHERE ID_Auteur = ?";
		
		try (Connection connection = dbManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateAuthor(Author author) {
	    String query = "UPDATE auteur SET Nom = ?, Prenom = ?, Date_Naissance = ? WHERE ID_Auteur = ?";
	    
	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        
	        statement.setString(1, author.getPrenom());
	        statement.setString(2, author.getNom());
	        
	        if (author.getDateNaissance() != null) {
	            statement.setDate(3, new java.sql.Date(author.getDateNaissance().getTime()));
	        } else {
	            statement.setNull(3, java.sql.Types.DATE);
	        }
	        
	        statement.setInt(4, author.getId());
	        
	        statement.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
}
