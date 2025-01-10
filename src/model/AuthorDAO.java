package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
}
