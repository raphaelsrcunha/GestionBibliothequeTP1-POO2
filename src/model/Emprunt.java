package model;

import java.util.Date;

public class Emprunt {
    private int idEmprunt;
    private int idLivre;
    private int idMembre;
    private Date dateEmprunt;
    private Date dateRetourPrevue;
    private Date dateRetourEffective;

    public Emprunt() {}

    public Emprunt(int idLivre, int idMembre, Date dateEmprunt, 
    	Date dateRetourPrevue, Date dateRetourEffective) {
    	this.idLivre = idLivre;
    	this.idMembre = idMembre;
    	this.dateEmprunt = dateEmprunt;
    	this.dateRetourPrevue = dateRetourPrevue;
    	this.dateRetourEffective = dateRetourEffective;
    }
    
    public Emprunt(int idEmprunt, int idLivre, int idMembre, Date dateEmprunt, 
    	Date dateRetourPrevue, Date dateRetourEffective) {
    	this.idEmprunt = idEmprunt;
    	this.idLivre = idLivre;
    	this.idMembre = idMembre;
    	this.dateEmprunt = dateEmprunt;
    	this.dateRetourPrevue = dateRetourPrevue;
    	this.dateRetourEffective = dateRetourEffective;
    }
    
    public int getIdEmprunt() {
        return idEmprunt;
    }

    public void setIdEmprunt(int idEmprunt) {
        this.idEmprunt = idEmprunt;
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

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "idEmprunt=" + idEmprunt +
                ", idLivre=" + idLivre +
                ", idMembre=" + idMembre +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                '}';
    }
}