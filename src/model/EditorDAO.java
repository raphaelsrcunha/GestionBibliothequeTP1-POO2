package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;

public class EditorDAO {
	
	private DatabaseManager dbManager;
	
	public EditorDAO() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public List<Editor> getAllEditors() {
		
		List<Editor> editors = new ArrayList<>();
		String query = "SELECT * FROM editeur";
		
		try {
			Connection connection = dbManager.getConnection();
			
			try (PreparedStatement statement = connection.prepareStatement(query);
				 ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					Editor editor = new Editor(
							resultSet.getInt("ID_Editeur"),
							resultSet.getString("Nom"),
							resultSet.getString("Adresse"));
					editors.add(editor);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return editors;
	}
	
	public void addEditor(Editor editor) {
		String query = "INSERT INTO editeur (Nom, Adresse) VALUES (?, ?)";
		
		try {
			Connection connection = dbManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, editor.getNom());
			statement.setString(2, editor.getAddress());
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEditor(int id) {
	    String query = "DELETE FROM editeur WHERE ID_Editeur = ?";
	    
	    try {
	        Connection connection = dbManager.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        
	        statement.setInt(1, id);
	        statement.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public void updateEditor(Editor editor) {
	    String query = "UPDATE editeur SET Nom = ?, Adresse = ? WHERE ID_Editeur = ?";
	    
	    try {
	        Connection connection = dbManager.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        
	        statement.setString(1, editor.getNom());
	        statement.setString(2, editor.getAddress());
	        statement.setInt(3, editor.getId());
	        statement.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Editor> searchByName(String name) {
	    List<Editor> editors = new ArrayList<>();
	    String query = "SELECT * FROM editeur WHERE Nom LIKE ?";
	    
	    try {
	        Connection connection = dbManager.getConnection();
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, "%" + name + "%");
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    Editor editor = new Editor(
	                        resultSet.getInt("ID_Editeur"),
	                        resultSet.getString("Nom"),
	                        resultSet.getString("Adresse")
	                    );
	                    editors.add(editor);
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return editors;
	}



}
