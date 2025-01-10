package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import db.DatabaseManager;

public class BookAuthorDAO {

	private DatabaseManager dbManager;
	
	public BookAuthorDAO() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public void addBookAuthor(BookAuthor bookAuthor) {
        String query = "INSERT INTO Livre_Auteur (ID_Livre, ID_Auteur) VALUES (?, ?)";

        try (Connection connection = dbManager.getConnection();
        		PreparedStatement statement = connection.prepareStatement(query)) 
        {
        	statement.setInt(1, bookAuthor.getIdBook());
         	statement.setInt(2, bookAuthor.getIdAuthor());
         	statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
