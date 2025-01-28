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
import model.BookDTO;
import model.Category;
import model.CategoryComponent;
import model.Editor;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookView extends JPanel {

	private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField titreTextField;
    private JTextField anneeDePublicationTextField;
    private JTextField isbnTextField;
    private JComboBox categoriesComboBox;
    private JComboBox nomEditeurComboBox;
    private JComboBox auteursComboBox;
    
    //Tree
    private JTree categoryTree;
    private DefaultMutableTreeNode rootCategoryNode;
    
    //Injections
    private BookController bookController;
    private CategoryController categoryController;
    private EditorController editorController;
    private AuthorController authorController;
    private BookAuthorController bookAuthorController;
    private JTextField rechercherParTitreTextField;
    
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
        
        //Tree
        rootCategoryNode = new DefaultMutableTreeNode("Categories");
        categoryTree = new JTree(rootCategoryNode);
        JScrollPane treeScrollPane = new JScrollPane(categoryTree);
        treeScrollPane.setBounds(35, 60, 170, 180);  
        add(treeScrollPane);

        JLabel lblNewLabel = new JLabel("Gestion des Livres");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(284, 11, 162, 25);
        add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 251, 730, 161);
        add(scrollPane);

        // Créer le modèle de la table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Titre");
        tableModel.addColumn("Auteurs");
        tableModel.addColumn("Année Publication");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Editeur");
        tableModel.addColumn("Catégorie");

        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1 && selectedRow < currentBooks.size()) {
                    BookDTO dto = currentBooks.get(selectedRow);
                    Book book = dto.getBook();
                    
                    titreTextField.setText(book.getTitre());
                    anneeDePublicationTextField.setText(String.valueOf(book.getAnnee_Publication()));
                    isbnTextField.setText(book.getISBN());

                    // Selecionar editor correto no combobox
                    for(int i = 0; i < nomEditeurComboBox.getItemCount(); i++) {
                        Object item = nomEditeurComboBox.getItemAt(i);
                        if(item instanceof Editor) {
                            Editor editor = (Editor) item;
                            if(editor.getId() == book.getID_Editeur()) {
                                nomEditeurComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                    }

                    // Selecionar categoria correta no combobox
                    for(int i = 0; i < categoriesComboBox.getItemCount(); i++) {
                        Object item = categoriesComboBox.getItemAt(i);
                        if(item instanceof Category) {
                            Category category = (Category) item;
                            if(category.getId() == book.getID_Categorie()) {
                                categoriesComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                    
                    //TODO
                 // Selection le author dans le combobox
                    for(int i = 0; i < auteursComboBox.getItemCount(); i++) {
                        Object item = auteursComboBox.getItemAt(i);
                        if(item instanceof Author) {
                            Author author = (Author) item;
                            if(author.getId() == book.getID_Categorie()) {
                                categoriesComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }
            }
        });
        scrollPane.setViewportView(table);
        
        titreTextField = new JTextField();
        titreTextField.setBounds(235, 82, 157, 20);
        add(titreTextField);
        titreTextField.setColumns(10);
        
        JLabel titreLabel = new JLabel("Titre");
        titreLabel.setBounds(235, 57, 52, 14);
        add(titreLabel);
        
        JLabel lblAnneDePublication = new JLabel("Année de Publication");
        lblAnneDePublication.setBounds(402, 66, 117, 14);
        add(lblAnneDePublication);
        
        anneeDePublicationTextField = new JTextField();
        anneeDePublicationTextField.setColumns(10);
        anneeDePublicationTextField.setBounds(402, 82, 117, 20);
        add(anneeDePublicationTextField);
        
        JLabel lblIsbn = new JLabel("ISBN");
        lblIsbn.setBounds(529, 66, 85, 14);
        add(lblIsbn);
        
        isbnTextField = new JTextField();
        isbnTextField.setColumns(10);
        isbnTextField.setBounds(529, 82, 117, 20);
        add(isbnTextField);
        
        nomEditeurComboBox = new JComboBox();
        nomEditeurComboBox.setBounds(235, 128, 124, 22);
        add(nomEditeurComboBox);
        
        JLabel lblEditeur = new JLabel("Editeur");
        lblEditeur.setBounds(234, 113, 117, 14);
        add(lblEditeur);
        
        JLabel lblCatgorie = new JLabel("Catégorie");
        lblCatgorie.setBounds(369, 113, 109, 14);
        add(lblCatgorie);
        
        categoriesComboBox = new JComboBox();
        categoriesComboBox.setBounds(369, 128, 131, 22);
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
        lblAuteur.setBounds(515, 113, 109, 14);
        add(lblAuteur);
        
        auteursComboBox = new JComboBox();
        auteursComboBox.setBounds(515, 128, 131, 22);
        add(auteursComboBox);
        
        JButton btnMettreAJour = new JButton("Mettre à jour");
        btnMettreAJour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                
                if(selectedRow == -1) {
                    JOptionPane.showMessageDialog(BookView.this, 
                        "Veuillez sélectionner un livre à modifier", 
                        "Aucune sélection", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int bookId = (int) tableModel.getValueAt(selectedRow, 0);
                    String titre = titreTextField.getText();
                    int annee = Integer.parseInt(anneeDePublicationTextField.getText());
                    String isbn = isbnTextField.getText();
                    
                    Editor selectedEditor = (Editor) nomEditeurComboBox.getSelectedItem();
                    Category selectedCategory = (Category) categoriesComboBox.getSelectedItem();
                    Author selectedAuthor = (Author) auteursComboBox.getSelectedItem();

                    if(selectedEditor == null || selectedCategory == null || selectedAuthor == null) {
                        JOptionPane.showMessageDialog(BookView.this, 
                            "Veuillez sélectionner un éditeur et une catégorie valides", 
                            "Données manquantes", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Book updatedBook = new Book(
                        bookId,
                        titre,
                        annee,
                        isbn,
                        selectedEditor.getId(),
                        selectedCategory.getId()
                    );
                    
                    BookAuthor updatedBookAuthor = new BookAuthor(
                    		bookId,
                    		selectedAuthor.getId()
                    		);

                    bookController.updateBook(updatedBook);
                    //bookAuthorController.update(updatedBookAuthor);
                    
                    loadBooks();
                    clearFields();
                    
                    JOptionPane.showMessageDialog(BookView.this, 
                        "Livre mis à jour avec succès!", 
                        "Mise à jour", 
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BookView.this, 
                        "Année de publication invalide", 
                        "Erreur de format", 
                        JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(BookView.this, 
                        "Erreur lors de la mise à jour: " + ex.getMessage(), 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnMettreAJour.setBounds(656, 146, 109, 23);
        add(btnMettreAJour);
        
        rechercherParTitreTextField = new JTextField();
        rechercherParTitreTextField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		rechercherParTitre();
        	}
        });
        rechercherParTitreTextField.setBounds(235, 220, 240, 20);
        add(rechercherParTitreTextField);
        rechercherParTitreTextField.setColumns(10);
        
        JButton btnRechercherParTitre = new JButton("Rechercher par Titre");
        btnRechercherParTitre.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		rechercherParTitre();
        	}
        });
        btnRechercherParTitre.setBounds(485, 217, 161, 23);
        add(btnRechercherParTitre);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadBooks();
        		clearFields();
        	}
        });
        btnRefresh.setBounds(656, 217, 109, 23);
        add(btnRefresh);

        //AGORA PRECISO IMPLEMENTAR A LÓGICA DE ATUALIZAR A TABELA LIVRE_AUTEUR
        
        loadCategories();
        loadEditors();
        loadAuthors();
        loadBooks();
    }
    
    private void rechercherParTitre() {
        List<Book> booksSearched = bookController.searchByTitle(rechercherParTitreTextField.getText());
        
        tableModel.setRowCount(0);

        for (Book book : booksSearched) {
            BookDTO dto = bookController.getBookDetails(book.getID_Livre());
            
            tableModel.addRow(new Object[]{
                book.getID_Livre(),
                book.getTitre(),
                dto.getAuteurs(),
                book.getAnnee_Publication(),
                book.getISBN(),
                dto.getEditeurNom(),
                dto.getCategorieNom()
            });
        }
    }
    
    private void addBook() {
    	String titre = titreTextField.getText();
    	int anneePublication = Integer.parseInt(anneeDePublicationTextField.getText());
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
        anneeDePublicationTextField.setText("");
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

    private List<BookDTO> currentBooks;

    private void loadBooks() {
    	currentBooks = bookController.getAllBooksWithDetails();
	    tableModel.setRowCount(0);
	    for (BookDTO dto : currentBooks) {
	        tableModel.addRow(new Object[]{
	            dto.getBook().getID_Livre(),
	            dto.getBook().getTitre(),
	            dto.getAuteurs(),
	            dto.getBook().getAnnee_Publication(),
	            dto.getBook().getISBN(),
	            dto.getEditeurNom(),
	            dto.getCategorieNom()
	        });
	    }
    }
    
    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un livre à supprimer.", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Agora o ID está na coluna 0
        int idLivre = (int) tableModel.getValueAt(selectedRow, 0); 
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce livre?", 
            "Confirmer la suppression", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            bookController.deleteBook(idLivre);
            loadBooks();
            JOptionPane.showMessageDialog(this, 
                "Livre supprimé avec succès!", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadCategories() {
    	List<Category> categories = categoryController.getAllCategories();
    	categoriesComboBox.addItem(null);
    	
    	rootCategoryNode.removeAllChildren();
    	
    	for(Category category : categories) {
    		DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category);
            rootCategoryNode.add(categoryNode);
            addSubCategories(categoryNode, category.getChildren());
    		categoriesComboBox.addItem(category);
    	}
    }
    
    private void addSubCategories(DefaultMutableTreeNode parentNode, List<CategoryComponent> subCategories) {
        for (CategoryComponent component : subCategories) {
            if (component instanceof Category) {
                DefaultMutableTreeNode subCategoryNode = new DefaultMutableTreeNode(component);
                parentNode.add(subCategoryNode);
                addSubCategories(subCategoryNode, ((Category) component).getChildren());
            }
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
    
    private void clearFields() {
    	titreTextField.setText("");
    	anneeDePublicationTextField.setText("");
    	isbnTextField.setText("");
    	nomEditeurComboBox.setSelectedIndex(0);
    	categoriesComboBox.setSelectedIndex(0);
    	auteursComboBox.setSelectedIndex(0);
    	rechercherParTitreTextField.setText("");
    }
    
    
}
