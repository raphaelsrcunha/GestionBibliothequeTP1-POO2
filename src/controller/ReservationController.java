package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseManager;
import model.Reservation;

public class ReservationController {

    private DatabaseManager dbManager;

    public ReservationController() {
        this.dbManager = DatabaseManager.getInstance();
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("ID_Livre"),
                    rs.getInt("ID_Membre"),
                    rs.getDate("Date_Reservation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public boolean addReservation(Reservation reservation) {
        String query = "INSERT INTO Reservation (ID_Livre, ID_Membre, Date_Reservation) VALUES (?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getIdLivre());
            stmt.setInt(2, reservation.getIdMembre());
            stmt.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime()));
            
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReservation(int idLivre, int idMembre) {
        String query = "DELETE FROM Reservation WHERE ID_Livre = ? AND ID_Membre = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idLivre);
            stmt.setInt(2, idMembre);
            
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsByBook(int bookId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation WHERE ID_Livre = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("ID_Livre"),
                    rs.getInt("ID_Membre"),
                    rs.getDate("Date_Reservation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void processReturnAndNotify(int bookId) {
        String query = "DELETE FROM Reservation WHERE ID_Livre = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}