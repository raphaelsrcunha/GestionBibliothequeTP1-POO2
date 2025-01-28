package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseManager;
import model.Member;

public class MemberController {

	private DatabaseManager dbManager;
	
	public MemberController() {
		this.dbManager = DatabaseManager.getInstance();
	}
	
	public List<Member> getAllMembers() {
		
		List<Member> members = new ArrayList<>();
		String query = "SELECT * FROM membre";
		
		try {
			Connection connection = dbManager.getConnection();
			
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					Member member = new Member(
							resultSet.getInt("ID_Membre"),
							resultSet.getString("Nom"),
							resultSet.getString("Prenom"),
							resultSet.getString("Email"),
							resultSet.getDate("Date_Inscription")
							); 
					members.add(member);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	public void addMember(Member member) {
		String query = "INSERT INTO membre (Nom, Prenom, Email, Date_Inscription) VALUES (?, ?, ?, ?)";
		
		try {
			Connection connection = dbManager.getConnection();
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, member.getNom());
				statement.setString(2, member.getPrenom());
				statement.setString(3, member.getEmail());
				statement.setDate(4, new java.sql.Date(member.getDateInscription().getTime()));
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMember(int id) {
		String query = "DELETE FROM membre WHERE ID_Membre = ?";
		
		try (Connection connection = dbManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, id);
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateMember(int id, Member member) {
		String query = "UPDATE membre SET Nom = ?, Prenom = ?, Email = ?, Date_Inscription = ? WHERE ID_Membre = ?";

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setString(1, member.getNom()); 
	        statement.setString(2, member.getPrenom()); 
	        statement.setString(3, member.getEmail()); 
	        statement.setDate(4, new java.sql.Date(member.getDateInscription().getTime())); 
	        statement.setInt(5, id); 

	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public Member getMember(int id) {
	    String query = "SELECT * FROM membre WHERE ID_Membre = ?";
	    Member member = null;

	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, id); // Define o parâmetro do ID na consulta
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                member = new Member(
	                    resultSet.getInt("ID_Membre"),
	                    resultSet.getString("Nom"),
	                    resultSet.getString("Prenom"),
	                    resultSet.getString("Email"),
	                    resultSet.getDate("Date_Inscription")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return member; 
	}

	public List<Member> findByEmail(String email) {
	    List<Member> members = new ArrayList<>();
	    String query = "SELECT * FROM membre WHERE Email LIKE ?";
	    
	    try (Connection connection = dbManager.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setString(1, "%" + email + "%");
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Member member = new Member(
	                    resultSet.getInt("ID_Membre"),
	                    resultSet.getString("Nom"),
	                    resultSet.getString("Prenom"),
	                    resultSet.getString("Email"),
	                    resultSet.getDate("Date_Inscription")
	                );
	                members.add(member);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return members;
	}

	public Member getMemberByPrenom(String prenom) {
        String query = "SELECT * FROM Membre WHERE Prenom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prenom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getInt("ID_Membre"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getDate("Date_Inscription")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
