package model;

import java.util.Date;

public class Reservation {
    private int idLivre;
    private int idMembre;
    private Date dateReservation;

    public Reservation() {}

    public Reservation(int idLivre, int idMembre, Date dateReservation) {
        this.idLivre = idLivre;
        this.idMembre = idMembre;
        this.dateReservation = dateReservation;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idLivre=" + idLivre +
                ", idMembre=" + idMembre +
                ", dateReservation=" + dateReservation +
                '}';
    }
}
