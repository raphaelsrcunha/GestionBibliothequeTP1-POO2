package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;

public class MemberDAO {
	
	private DatabaseManager dbManager;
	
	public MemberDAO() {
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

}
