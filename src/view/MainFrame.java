package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.AuthorController;
import controller.BookAuthorController;
import controller.BookController;
import controller.CategoryController;
import controller.EditorController;
import db.DatabaseManager;
import model.AuthorDAO;
import model.BookAuthorDAO;
import model.BookDAO;
import model.CategoryDAO;
import model.EditorDAO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel homeContentPane;
	private static DatabaseManager dbManager;

	/*Launch the application.*/
	public static void main(String[] args) {
		
		dbManager = DatabaseManager.getInstance();

        try {
            dbManager.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Crée les instances et injecte les dépendances
					// Dependency Injection and Inversion of Control
                    BookDAO bookDAO = new BookDAO();
                    CategoryDAO categoryDAO = new CategoryDAO();
                    EditorDAO editorDAO = new EditorDAO();
                    BookController bookController = new BookController(bookDAO);
                    CategoryController categoryController = new CategoryController(categoryDAO);
                    EditorController editorController = new EditorController(editorDAO);
                    AuthorDAO authorDAO = new AuthorDAO();
                    AuthorController authorController = new AuthorController(authorDAO);
                    BookAuthorDAO bookAuthorDAO = new BookAuthorDAO();
                    BookAuthorController bookAuthorController = new BookAuthorController(bookAuthorDAO);
                    
					MainFrame frame = new MainFrame(bookController, categoryController, editorController, authorController, bookAuthorController);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/*Create the frame.*/
	public MainFrame(BookController bookController, CategoryController categoryController, EditorController editorController, AuthorController authorController,
			BookAuthorController bookAuthorController) {
		setTitle("Gestion de Bibliothèque - TP1 - POO2 (Raphael Santarosa da Cunha)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Fichier");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem ecranPrincipalMenuItem = new JMenuItem("Écran Principal");
		ecranPrincipalMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(homeContentPane);
			}
		});
		mnNewMenu_1.add(ecranPrincipalMenuItem);
		
		JMenuItem fermerMenuItem = new JMenuItem("Fermer");
		mnNewMenu_1.add(fermerMenuItem);
		
		JMenu mnNewMenu = new JMenu("Modules");
		menuBar.add(mnNewMenu);
		
		JMenuItem gestionDesLivresMenuItem = new JMenuItem("Gestion des Livres");
		gestionDesLivresMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                BookView livresView = new BookView(bookController, categoryController, editorController, authorController, bookAuthorController);

                setContentPane(livresView);
                revalidate();
                repaint();
            }
        });
		mnNewMenu.add(gestionDesLivresMenuItem);
		
		JMenuItem gestionDesMembresMenuItem = new JMenuItem("Gestion des Membres");
		gestionDesMembresMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MembresView membresView = new MembresView();

                setContentPane(membresView);
                revalidate();
                repaint();
            }
        });
		mnNewMenu.add(gestionDesMembresMenuItem);
		
		JMenuItem gestionDesEmpruntsMenuItem = new JMenuItem("Gestion des Emprunts");
		gestionDesEmpruntsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EmpruntsView empruntsView = new EmpruntsView();
				
				setContentPane(empruntsView);
				revalidate();
				repaint();
			}
		});
		mnNewMenu.add(gestionDesEmpruntsMenuItem);
		
		JMenuItem gestionDesAuteursMenuItem = new JMenuItem("Gestion des Auteurs");
		gestionDesAuteursMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuteurView auteurView = new AuteurView(authorController);
				
				setContentPane(auteurView);
				revalidate();
				repaint();
			}
		});
		mnNewMenu.add(gestionDesAuteursMenuItem);
		
		JMenuItem gestionDesEditeursMenuItem = new JMenuItem("Gestion des Editeurs");
		gestionDesEditeursMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditeurView editeurView = new EditeurView(editorController);
				
				setContentPane(editeurView);
				revalidate();
				repaint();
			}
		});
		mnNewMenu.add(gestionDesEditeursMenuItem);
		
		JMenu mnNewMenu_2 = new JMenu("À Propos");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("À Propos de nous");
		mnNewMenu_2.add(mntmNewMenuItem);
		
		homeContentPane = new JPanel();
		homeContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(homeContentPane);
		homeContentPane.setLayout(null);
		
		JLabel titreMainFrame = new JLabel("Application de Gestion des Bibliothèques");
		titreMainFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titreMainFrame.setFont(new Font("Tahoma", Font.BOLD, 16));
		titreMainFrame.setBounds(203, 11, 355, 25);
		homeContentPane.add(titreMainFrame);
		
		JLabel titreTP = new JLabel("Travail Pratique 1 - POO2");
		titreTP.setHorizontalAlignment(SwingConstants.CENTER);
		titreTP.setFont(new Font("Tahoma", Font.BOLD, 16));
		titreTP.setBounds(215, 179, 355, 25);
		homeContentPane.add(titreTP);
		
		JLabel subtitre = new JLabel("Elève: Raphael Santarosa da Cunha");
		subtitre.setHorizontalAlignment(SwingConstants.CENTER);
		subtitre.setFont(new Font("Tahoma", Font.PLAIN, 10));
		subtitre.setBounds(215, 403, 355, 25);
		homeContentPane.add(subtitre);
		
		// Pour charger l'image logo
		try {
		    java.net.URL imageURL = getClass().getResource("/assets/logo.png");
		    if (imageURL == null) {
		        System.err.println("Error");
		    } else {
		        ImageIcon logoIcon = new ImageIcon(imageURL);
		        JLabel logoLabel = new JLabel(logoIcon);
		        logoLabel.setBounds(300, 50, 200, 100); 
		        homeContentPane.add(logoLabel);
		    }
		} catch (Exception e) {
		    System.err.println("Error: " + e.getMessage());
		    e.printStackTrace();
		}
		
		//Fermer la connexion avec le BD quand la fenêtre est fermée
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (dbManager != null) {
                        dbManager.disconnect();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
	}
}
