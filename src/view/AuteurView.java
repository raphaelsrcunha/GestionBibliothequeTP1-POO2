package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.AuthorController;
import model.Author;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuteurView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private AuthorController authorController;
	private JTextField textFieldId;
	private JTextField textFieldPrenom;
	private JTextField textFieldNom;
	private JTextField textFieldDateDeNaissance;

	//dependency injection
	public AuteurView(AuthorController authorController) {
		this.authorController = authorController;
		initializeUI();
	}
	
	private void initializeUI() {
		
		setLayout(null);
		
		JLabel lblGestionDesAuteurs = new JLabel("Gestion des Auteurs");
		lblGestionDesAuteurs.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionDesAuteurs.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGestionDesAuteurs.setBounds(282, 11, 194, 25);
		add(lblGestionDesAuteurs);

		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(44, 216, 718, 188);
		add(scrollPane);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Prenom");
		tableModel.addColumn("Date Naissance");
		
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
        		
        		if(selectedRow != -1) {
        			String id = tableModel.getValueAt(selectedRow, 0).toString();
        			String prenom = tableModel.getValueAt(selectedRow, 1).toString();
        			String nom = tableModel.getValueAt(selectedRow, 2).toString();
        			String dateDeNaissance = tableModel.getValueAt(selectedRow, 3).toString();
        			
        			textFieldId.setText(id);
        			textFieldPrenom.setText(prenom);
        			textFieldNom.setText(nom);
        			textFieldDateDeNaissance.setText(dateDeNaissance);
        	
        		}
			}
		});
		scrollPane.setViewportView(table);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(44, 57, 145, 20);
		add(textFieldId);
		textFieldId.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(44, 43, 145, 14);
		add(lblNewLabel);
		
		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setBounds(44, 88, 145, 14);
		add(lblPrenom);
		
		textFieldPrenom = new JTextField();
		textFieldPrenom.setColumns(10);
		textFieldPrenom.setBounds(44, 102, 145, 20);
		add(textFieldPrenom);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nom");
		lblNewLabel_1_1.setBounds(44, 132, 145, 14);
		add(lblNewLabel_1_1);
		
		textFieldNom = new JTextField();
		textFieldNom.setColumns(10);
		textFieldNom.setBounds(44, 146, 145, 20);
		add(textFieldNom);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date de Naissance");
		lblNewLabel_1_1_1.setBounds(44, 177, 145, 14);
		add(lblNewLabel_1_1_1);
		
		textFieldDateDeNaissance = new JTextField();
		textFieldDateDeNaissance.setColumns(10);
		textFieldDateDeNaissance.setBounds(44, 191, 145, 20);
		add(textFieldDateDeNaissance);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAuteur();
			}
		});
		btnAjouter.setBounds(220, 56, 100, 23);
		add(btnAjouter);
		
		JButton btnMisAJour = new JButton("Mis à Jour");
		btnMisAJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateAuteur();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnMisAJour.setBounds(220, 101, 100, 23);
		add(btnMisAJour);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAuteur();
			}
		});
		btnSupprimer.setBounds(222, 146, 100, 23);
		add(btnSupprimer);
		
		loadAuteurs();
	}
	
	private void addAuteur() {
		try {
			Author auteur = new Author(
					textFieldNom.getText(),
					textFieldPrenom.getText(),
					new SimpleDateFormat("yyyy-MM-dd").parse(textFieldDateDeNaissance.getText())
					);
			authorController.addAuthor(auteur);
			loadAuteurs();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		clearFields();
	}
	
	private void deleteAuteur() {
		int selectedId = Integer.parseInt(textFieldId.getText());
		authorController.deleteAuthor(selectedId);
		loadAuteurs();
		clearFields();
	}
	
	private void updateAuteur() throws ParseException {
		Author auteur = new Author(
				Integer.parseInt(textFieldId.getText()),
				textFieldNom.getText(),
				textFieldPrenom.getText(),
				new SimpleDateFormat("yyyy-MM-dd").parse(textFieldDateDeNaissance.getText())
				);
		authorController.updateAuthor(auteur);
		loadAuteurs();
		clearFields();
	}
	
	private void loadAuteurs() {
		List<Author> autheurs = authorController.getAllAuthors();
		tableModel.setRowCount(0);
		
		for (Author author : autheurs) {
			tableModel.addRow(new Object[] {
					author.getId(),
					author.getNom(),
					author.getPrenom(),
					author.getDateNaissance()
			});
		}
	}
	
	private void clearFields() {
		textFieldId.setText("");
		textFieldPrenom.setText("");
		textFieldNom.setText("");
		textFieldDateDeNaissance.setText("");
	}
	
}
