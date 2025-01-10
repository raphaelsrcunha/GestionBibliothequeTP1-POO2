package model;

public class Book implements CategoryComponent {

	 private int ID_Livre; 
	 private String Titre;
	 private int Annee_Publication;
	 private String ISBN;
	 private int ID_Editeur;
	 private int ID_Categorie;

    public Book(int ID_Livre, String Titre, int Annee_Publication, String ISBN, int ID_Editeur, int ID_Categorie) {
        this.ID_Livre = ID_Livre;
        this.Titre = Titre;
        this.Annee_Publication = Annee_Publication;
        this.ISBN = ISBN;
        this.ID_Editeur = ID_Editeur;
        this.ID_Categorie = ID_Categorie;
    }
    
    public Book(String Titre, int Annee_Publication, String ISBN, int ID_Editeur, int ID_Categorie) {
        this.Titre = Titre;
        this.Annee_Publication = Annee_Publication;
        this.ISBN = ISBN;
        this.ID_Editeur = ID_Editeur;
        this.ID_Categorie = ID_Categorie;
    }

    public int getID_Livre() {
        return ID_Livre;
    }

    public void setID_Livre(int ID_Livre) {
        this.ID_Livre = ID_Livre;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public int getAnnee_Publication() {
        return Annee_Publication;
    }

    public void setAnnee_Publication(int Annee_Publication) {
        this.Annee_Publication = Annee_Publication;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getID_Editeur() {
        return ID_Editeur;
    }

    public void setID_Editeur(int ID_Editeur) {
       this.ID_Editeur = ID_Editeur;
   }

    public int getID_Categorie() {
        return ID_Categorie;
    }

    public void setID_Categorie(int ID_Categorie) {
        this.ID_Categorie = ID_Categorie;
    }

    @Override
    public void display() {
        System.out.println("Livre: " + Titre + " (ISBN: " + ISBN + ")");
    }

    @Override
    public void add(CategoryComponent component) {
        System.out.println("Cette opération n'est pas supportée");
    }

    @Override
    public void remove(CategoryComponent component) {
        System.out.println("Cette opération n'est pas supportée");
    }

    @Override
    public String toString() {
        return "Livre{" +
                "ID_Livre=" + ID_Livre +
                ", Titre='" + Titre + '\'' +
                ", Annee_Publication=" + Annee_Publication +
                ", ISBN='" + ISBN + '\'' +
                ", ID_Editeur=" + ID_Editeur +
                ", ID_Categorie=" + ID_Categorie +
                '}';
    }
}
