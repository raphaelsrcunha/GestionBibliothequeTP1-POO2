package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.AuthorController;
import model.Author;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AuteurView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private AuthorController authorController;

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
		scrollPane.setViewportView(table);
		
		loadAuteurs();
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

}
