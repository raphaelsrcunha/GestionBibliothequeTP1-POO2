package model;

import java.util.Date;

import javax.swing.JOptionPane;

public class Member implements Observer {

    private int idMembre;
    private String nom;
    private String prenom;
    private String email;
    private Date dateInscription;

    public Member(int idMembre, String nom, String prenom, String email, Date dateInscription) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = dateInscription;
    }

    public Member(String nom, String prenom, String email, Date dateInscription) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = dateInscription;
    }

    public Member() {}

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }

    @Override
    public void update(Book book) {
        String message = String.format(
            "<html><body style='width: 300px; text-align: center'>" +
            "Le livre <strong>%s</strong> est maintenant disponible!" +
            "</body></html>", 
            book.getTitre()
        );

        JOptionPane.showMessageDialog(
            null,
            message,
            "Livre Disponible",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

}