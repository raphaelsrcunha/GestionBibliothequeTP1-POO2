package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<BookDTO> getAllBooksWithDetails() {
	    List<BookDTO> booksDTO = new ArrayList<>();
	    String query = "SELECT " +
	                   "livre.ID_Livre, " +
	                   "livre.Titre, " +
	                   "livre.Annee_Publication, " +
	                   "livre.ISBN, " +
	                   "livre.ID_Editeur, " +
	                   "livre.ID_Categorie, " +
	                   "editeur.Nom AS EditeurNom, " +
	                   "categorie.Nom AS CategorieNom, " +
	                   "GROUP_CONCAT(auteur.Nom SEPARATOR ', ') AS Auteurs " +
	                   "FROM livre " +
	                   "LEFT JOIN editeur ON livre.ID_Editeur = editeur.ID_Editeur " +
	                   "LEFT JOIN categorie ON livre.ID_Categorie = categorie.ID_Categorie " +
	                   "LEFT JOIN livre_auteur ON livre.ID_Livre = livre_auteur.ID_Livre " +
	                   "LEFT JOIN auteur ON livre_auteur.ID_Auteur = auteur.ID_Auteur " +
	                   "GROUP BY " +
	                   "livre.ID_Livre, livre.Titre, livre.Annee_Publication, livre.ISBN, " +
	                   "editeur.Nom, categorie.Nom";

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            // Cria o objeto Book com os dados básicos
	            Book book = new Book(
	                resultSet.getInt("ID_Livre"),
	                resultSet.getString("Titre"),
	                resultSet.getInt("Annee_Publication"),
	                resultSet.getString("ISBN"),
	                resultSet.getInt("ID_Editeur"),
	                resultSet.getInt("ID_Categorie")
	            );

	            // Cria o DTO com os dados adicionais
	            BookDTO bookDTO = new BookDTO(
	                book,
	                resultSet.getString("EditeurNom"), // Nome do editor
	                resultSet.getString("CategorieNom"), // Nome da categoria
	                resultSet.getString("Auteurs") // Lista de autores concatenada
	            );

	            booksDTO.add(bookDTO);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return booksDTO;
	}

	
	public BookDTO getBookDetailsById(int bookId) {
	    String query = """
	        SELECT 
	            livre.ID_Livre, 
	            livre.Titre, 
	            livre.Annee_Publication, 
	            livre.ISBN, 
	            editeur.Nom AS EditeurNom, 
	            categorie.Nom AS CategorieNom, 
	            GROUP_CONCAT(auteur.Nom SEPARATOR ', ') AS Auteurs
	        FROM 
	            livre
	        LEFT JOIN 
	            editeur ON livre.ID_Editeur = editeur.ID_Editeur
	        LEFT JOIN 
	            categorie ON livre.ID_Categorie = categorie.ID_Categorie
	        LEFT JOIN 
	            livre_auteur ON livre.ID_Livre = livre_auteur.ID_Livre
	        LEFT JOIN 
	            auteur ON livre_auteur.ID_Auteur = auteur.ID_Auteur
	        WHERE 
	            livre.ID_Livre = ?
	        GROUP BY 
	            livre.ID_Livre, 
	            livre.Titre, 
	            livre.Annee_Publication, 
	            livre.ISBN, 
	            editeur.Nom, 
	            categorie.Nom;
	        """;

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, bookId);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                // Construir o Book
	                Book book = new Book(
	                    resultSet.getInt("ID_Livre"),
	                    resultSet.getString("Titre"),
	                    resultSet.getInt("Annee_Publication"),
	                    resultSet.getString("ISBN"),
	                    resultSet.getInt("ID_Editeur"),
	                    resultSet.getInt("ID_Categorie")
	                );

	                // Obter os autores
	                String authors = resultSet.getString("Auteurs");

	                // Construir o DTO
	                return new BookDTO(
	                    book,
	                    resultSet.getString("EditeurNom"),
	                    resultSet.getString("CategorieNom"),
	                    authors // Adiciona os nomes dos autores no DTO
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; // Retorna null se o livro não for encontrado
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
	

	
	public Book getBook(int id) {
	    String query = "SELECT * FROM Livre WHERE ID_Livre = ?";
	    Book book = null;

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, id);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                book = new Book(
	                    resultSet.getInt("ID_Livre"),
	                    resultSet.getString("Titre"),
	                    resultSet.getInt("Annee_Publication"),
	                    resultSet.getString("ISBN"),
	                    resultSet.getInt("ID_Editeur"),
	                    resultSet.getInt("ID_Categorie")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return book; 
	}

	
	public boolean isBookAvailable(int bookId) {
        String query = """
            SELECT CASE 
                WHEN NOT EXISTS (SELECT 1 FROM Emprunt WHERE ID_Livre = ? AND Date_Retour_Effective IS NULL) THEN 1
                ELSE 0
            END AS Disponible
            """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Disponible") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public List<Book> searchByTitle(String title) {
	    List<Book> books = new ArrayList<>();
	    String query = "SELECT * FROM livre WHERE Titre LIKE ?";
	    
	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        
	        statement.setString(1, "%" + title + "%");
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
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
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return books;
	}

    public Book getBookByTitle(String title) {
        String query = "SELECT * FROM Livre WHERE Titre = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("ID_Livre"),
                        rs.getString("Titre"),
                        rs.getInt("Annee_Publication"),
                        rs.getString("ISBN"),
                        rs.getInt("ID_Editeur"),
                        rs.getInt("ID_Categorie")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }	
    
    public boolean updateBook(Book book) {
        String query = "UPDATE Livre SET Titre = ?, Annee_Publication = ?, ISBN = ?, ID_Editeur = ?, ID_Categorie = ? WHERE ID_Livre = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitre());
            statement.setInt(2, book.getAnnee_Publication());
            statement.setString(3, book.getISBN());
            statement.setInt(4, book.getID_Editeur());
            statement.setInt(5, book.getID_Categorie());
            statement.setInt(6, book.getID_Livre()); 

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; 
    }

}
