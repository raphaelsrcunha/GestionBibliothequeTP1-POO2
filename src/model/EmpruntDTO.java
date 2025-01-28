package model;

public class EmpruntDTO {
	
    private int idEmprunt;
    private int idLivre;
    private String titreLivre;
    private int idMembre;
    private String prenomMembre;
    private String nomMembre;
    private java.sql.Date dateEmprunt;
    private java.sql.Date dateRetourPrevue;
    private java.sql.Date dateRetourEffective;

    public EmpruntDTO() {
    }

    public EmpruntDTO(int idEmprunt, int idLivre, String titreLivre, int idMembre, String prenomMembre, String nomMembre, 
                      java.sql.Date dateEmprunt, java.sql.Date dateRetourPrevue, java.sql.Date dateRetourEffective) {
        this.idEmprunt = idEmprunt;
        this.idLivre = idLivre;
        this.titreLivre = titreLivre;
        this.prenomMembre = prenomMembre;
        this.idMembre = idMembre;
        this.nomMembre = nomMembre;
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

    public String getTitreLivre() {
        return titreLivre;
    }

    public void setTitreLivre(String titreLivre) {
        this.titreLivre = titreLivre;
    }
    
    public int getIdMembre() {
    	return idMembre;
    }
    
    public void setIdMembre(int idMembre) {
    	this.idMembre = idMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public java.sql.Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(java.sql.Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public java.sql.Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(java.sql.Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public java.sql.Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(java.sql.Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    @Override
    public String toString() {
        return "EmpruntDTO{" +
                "idEmprunt=" + idEmprunt +
                ", titreLivre='" + titreLivre + '\'' +
                ", prenomMembre='" + prenomMembre + '\'' +
                ", nomMembre='" + nomMembre + '\'' +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                '}';
    }
}