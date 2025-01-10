package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.AuthorController;
import controller.BookAuthorController;
import controller.BookController;
import controller.CategoryController;
import controller.EditorController;
import model.Author;
import model.Book;
import model.BookAuthor;
import model.Category;
import model.Editor;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookView extends JPanel {

	private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField titreTextField;
    private JTextField anneeDePublicationtextField;
    private JTextField isbnTextField;
    private JComboBox categoriesComboBox;
    private JComboBox nomEditeurComboBox;
    private JComboBox auteursComboBox;
    
    //Injections
    private BookController bookController;
    private CategoryController categoryController;
    private EditorController editorController;
    private AuthorController authorController;
    private BookAuthorController bookAuthorController;
    
    // dependency injection
    public BookView(BookController bookController, CategoryController categoryController, EditorController editorController, AuthorController authorController,
    		BookAuthorController bookAuthorController) {
        this.bookController = bookController;
        this.categoryController = categoryController;
        this.editorController = editorController;
        this.authorController = authorController;
        this.bookAuthorController = bookAuthorController;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Gestion des Livres");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(284, 11, 162, 25);
        add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 264, 730, 142);
        add(scrollPane);

        // Créer le modèle de la table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Titre");
        tableModel.addColumn("Annee Publication");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("ID Editeur");
        tableModel.addColumn("ID Categorie");

        table = new JTable(tableModel);
        scrollPane.setViewportView(table);
        
        titreTextField = new JTextField();
        titreTextField.setBounds(35, 82, 318, 20);
        add(titreTextField);
        titreTextField.setColumns(10);
        
        JLabel titreLabel = new JLabel("Titre");
        titreLabel.setBounds(35, 66, 52, 14);
        add(titreLabel);
        
        JLabel lblAnneDePublication = new JLabel("Année de Publication");
        lblAnneDePublication.setBounds(396, 66, 131, 14);
        add(lblAnneDePublication);
        
        anneeDePublicationtextField = new JTextField();
        anneeDePublicationtextField.setColumns(10);
        anneeDePublicationtextField.setBounds(396, 82, 131, 20);
        add(anneeDePublicationtextField);
        
        JLabel lblIsbn = new JLabel("ISBN");
        lblIsbn.setBounds(35, 116, 117, 14);
        add(lblIsbn);
        
        isbnTextField = new JTextField();
        isbnTextField.setColumns(10);
        isbnTextField.setBounds(35, 132, 149, 20);
        add(isbnTextField);
        
        nomEditeurComboBox = new JComboBox();
        nomEditeurComboBox.setBounds(194, 130, 117, 22);
        add(nomEditeurComboBox);
        
        JLabel lblEditeur = new JLabel("Editeur");
        lblEditeur.setBounds(194, 115, 109, 14);
        add(lblEditeur);
        
        JLabel lblCatgorie = new JLabel("Catégorie");
        lblCatgorie.setBounds(321, 115, 109, 14);
        add(lblCatgorie);
        
        categoriesComboBox = new JComboBox();
        categoriesComboBox.setBounds(321, 130, 131, 22);
        add(categoriesComboBox);
        
        JButton ajouterLivreButton = new JButton("Ajouter");
        ajouterLivreButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Ajouter un livre dans le DB
        		addBook();
        	}
        });
        ajouterLivreButton.setBounds(656, 81, 109, 23);
        add(ajouterLivreButton);
        
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteBook();
        	}
        });
        btnSupprimer.setBounds(656, 112, 109, 23);
        add(btnSupprimer);
        
        JLabel lblAuteur = new JLabel("Auteur");
        lblAuteur.setBounds(467, 115, 109, 14);
        add(lblAuteur);
        
        auteursComboBox = new JComboBox();
        auteursComboBox.setBounds(467, 130, 131, 22);
        add(auteursComboBox);

        //AGORA PRECISO IMPLEMENTAR A LÓGICA DE ATUALIZAR A TABELA LIVRE_AUTEUR
        
        loadCategories();
        loadEditors();
        loadAuthors();
        loadBooks();
    }
    
    private void addBook() {
    	String titre = titreTextField.getText();
    	int anneePublication = Integer.parseInt(anneeDePublicationtextField.getText());
        String isbn = isbnTextField.getText();
        Editor selectedEditor = (Editor) nomEditeurComboBox.getSelectedItem();
        Category selectedCategory = (Category) categoriesComboBox.getSelectedItem();
        Author selectedAuthor = (Author) auteursComboBox.getSelectedItem();
        
        Book newBook = new Book(0, titre, anneePublication, isbn, selectedEditor.getId(), selectedCategory.getId());
        
        int idLivre = bookController.addBook(newBook);
        
        BookAuthor bookAuthor = new BookAuthor(idLivre, selectedAuthor.getId());
        bookAuthorController.addBookAuthor(bookAuthor);

        loadBooks();

        titreTextField.setText("");
        anneeDePublicationtextField.setText("");
        isbnTextField.setText("");
        nomEditeurComboBox.setSelectedIndex(0);
        categoriesComboBox.setSelectedIndex(0);
        auteursComboBox.setSelectedIndex(0);
        JOptionPane.showMessageDialog(
        	    null, 
        	    "Livre ajouté avec succès!", 
        	    "Succès", 
        	    JOptionPane.INFORMATION_MESSAGE
        	);

    }

    private void loadBooks() {
        List<Book> books = bookController.getAllBooks();

        tableModel.setRowCount(0);

        //Ajoute les livres dans la table
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                book.getID_Livre(),
                book.getTitre(),
                book.getAnnee_Publication(),
                book.getISBN(),
                book.getID_Editeur(),
                book.getID_Categorie()
            });
        }
    }
    
    private void deleteBook() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idLivre = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce livre?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            bookController.deleteBook(idLivre);

            loadBooks();

            System.out.println("Livre supprimé avec succès!");
        }
    }
    
    private void loadCategories() {
    	List<Category> categories = categoryController.getAllCategories();
    	categoriesComboBox.addItem(null);
    	for(Category category : categories) {
    		categoriesComboBox.addItem(category);
    	}
    }
    
    private void loadEditors() {
    	List<Editor> editors = editorController.getAllEditors();
    	nomEditeurComboBox.addItem("");
    	for(Editor editor : editors) {
    		nomEditeurComboBox.addItem(editor);
    	}
    }
    
    private void loadAuthors() {
    	List<Author> authors = authorController.getAllAuthors();
    	auteursComboBox.addItem("");
    	for(Author author : authors) {
    		auteursComboBox.addItem(author);
    	}
    			
    }
}
