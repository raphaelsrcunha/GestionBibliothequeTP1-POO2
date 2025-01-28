package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseManager;

public class CategoryDAO {

    private DatabaseManager dbManager;

    public CategoryDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    // Retorna a categoria raiz com subcategorias organizadas
    public Category getRootCategory() {
        Category root = new Category(0, "Toutes les catégories", 0);
        populateChildren(root);
        return root;
    }

    // Preenche recursivamente as subcategorias
    private void populateChildren(Category parent) {
        List<Category> subCategories = getSubCategories(parent.getId());
        for (Category subCat : subCategories) {
            parent.add(subCat);
            populateChildren(subCat); // Recursão
        }
    }

    // Busca subcategorias de um pai específico
    private List<Category> getSubCategories(int parentId) {
        List<Category> subCategories = new ArrayList<>();
        String query = "SELECT * FROM Categorie WHERE ID_Categorie_Parent = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("ID_Categorie"),
                    rs.getString("Nom"),
                    rs.getInt("ID_Categorie_Parent")
                );
                subCategories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategories;
    }

    // Busca todas as categorias (opcional)
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categorie";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("ID_Categorie"),
                    rs.getString("Nom"),
                    rs.getInt("ID_Categorie_Parent")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    public void addCategory(String name, int parentId) {
        String query = "INSERT INTO Categorie (Nom, ID_Categorie_Parent) VALUES (?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, name);
            stmt.setInt(2, parentId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}