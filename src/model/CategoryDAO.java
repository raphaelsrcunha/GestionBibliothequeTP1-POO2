package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;

public class CategoryDAO {

	private DatabaseManager dbManager;

    public CategoryDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    public List<Category> getAllCategories() {
    	List<Category> categories = new ArrayList<>();
    	String query = "SELECT ID_Categorie, Nom FROM Categorie";
    	
    	try {
    		Connection connection = dbManager.getConnection();
    		try(PreparedStatement statement = connection.prepareStatement(query);
    			ResultSet resultSet = statement.executeQuery()) {
    			while(resultSet.next()) {
    				Category category = new Category(
    						resultSet.getInt("ID_Categorie"),
    						resultSet.getString("Nom")
    						);
    				categories.add(category);
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return categories;
    }
    
}
