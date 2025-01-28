package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DatabaseManager;
import model.Emprunt;
import model.EmpruntDTO;

public class EmpruntController {

	private DatabaseManager dbManager;
	
	public EmpruntController() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public void addEmprunt(Emprunt emprunt) {
	    String query = "INSERT INTO emprunt (ID_Livre, ID_Membre, Date_Emprunt, Date_Retour_Prevue, Date_Retour_Effective) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, emprunt.getIdLivre());
	        statement.setInt(2, emprunt.getIdMembre());
	        statement.setDate(3, new java.sql.Date(emprunt.getDateEmprunt().getTime()));
	        statement.setDate(4, new java.sql.Date(emprunt.getDateRetourPrevue().getTime()));

	        if (emprunt.getDateRetourEffective() != null) {
	            statement.setDate(5, new java.sql.Date(emprunt.getDateRetourEffective().getTime()));
	        } else {
	            statement.setNull(5, java.sql.Types.DATE);
	        }

	        statement.executeUpdate();
	        System.out.println("Emprunt ajouté avec succès: " + emprunt);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public List<Emprunt> getAllEmprunts() {
		List<Emprunt> emprunts = new ArrayList<>();
		String query = "SELECT * FROM emprunt";
		
		try {
			Connection connection = dbManager.getConnection();
			
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					Emprunt emprunt = new Emprunt(
							resultSet.getInt(1),
							resultSet.getInt(2),
							resultSet.getInt(3),
							resultSet.getDate(4),
							resultSet.getDate(5),
							resultSet.getDate(6)
							);
					emprunts.add(emprunt);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return emprunts;
	}
	
	public List<EmpruntDTO> getAllEmpruntsDTO() {
	    List<EmpruntDTO> empruntsDTO = new ArrayList<>();
	    String query = "SELECT emprunt.ID_Emprunt, emprunt.ID_Livre, livre.Titre AS TitreLivre, emprunt.ID_Membre, "
	                 + "membre.Prenom AS PrenomMembre, membre.Nom AS NomMembre, emprunt.Date_Emprunt, "
	                 + "emprunt.Date_Retour_Prevue, emprunt.Date_Retour_Effective "
	                 + "FROM emprunt "
	                 + "LEFT JOIN livre ON emprunt.ID_Livre = livre.ID_Livre "
	                 + "LEFT JOIN membre ON emprunt.ID_Membre = membre.ID_Membre";

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            EmpruntDTO empruntDTO = new EmpruntDTO(
	                resultSet.getInt("ID_Emprunt"),
	                resultSet.getInt("ID_Livre"),
	                resultSet.getString("TitreLivre"),
	                resultSet.getInt("ID_Membre"),
	                resultSet.getString("PrenomMembre"),
	                resultSet.getString("NomMembre"),
	                resultSet.getDate("Date_Emprunt"),
	                resultSet.getDate("Date_Retour_Prevue"),
	                resultSet.getDate("Date_Retour_Effective")
	            );
	            empruntsDTO.add(empruntDTO);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return empruntsDTO;
	}


	
	public void deleteEmprunt(int empruntId) {
        String query = "DELETE FROM emprunt WHERE ID_Emprunt = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empruntId);

            statement.executeUpdate();
            System.out.println("Emprunt supprimé avec succès: ID " + empruntId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAsReturned(int empruntId) {
        String query = "UPDATE emprunt SET Date_Retour_Effective = ? WHERE ID_Emprunt = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, new java.sql.Date(new Date().getTime())); 
            statement.setInt(2, empruntId);

            statement.executeUpdate();
            System.out.println("Emprunt marqué comme retourné: ID " + empruntId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAsNotReturned(int empruntId) {
        String query = "UPDATE emprunt SET Date_Retour_Effective = NULL WHERE ID_Emprunt = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empruntId);

            statement.executeUpdate();
            System.out.println("Emprunt marqué comme non retourné: ID " + empruntId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
