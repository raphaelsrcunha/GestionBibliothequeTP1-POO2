package model;

public class BookDTO {
    private Book book; 
    private String editeurNom; 
    private String categorieNom; 
    private String auteurs; 

    public BookDTO(Book book, String editeurNom, String categorieNom, String auteurs) {
        this.book = book;
        this.editeurNom = editeurNom;
        this.categorieNom = categorieNom;
        this.auteurs = auteurs;
    }

    // Getters
    public Book getBook() {
        return book;
    }

    public String getEditeurNom() {
        return editeurNom;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public String getAuteurs() {
        return auteurs;
    }

    // Setter para os autores (opcional, caso precise alterar posteriormente)
    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    @Override
    public String toString() {
        return "BookDTO {" +
                "book=" + book +
                ", editeurNom='" + editeurNom + '\'' +
                ", categorieNom='" + categorieNom + '\'' +
                ", auteurs='" + auteurs + '\'' +
                '}';
    }
}
