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

}
