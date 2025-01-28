package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.EditorController;
import model.Book;
import model.Editor;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditeurView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private EditorController editorController;
	private JTextField idTextField;
	private JTextField nomTextField;
	private JTextField adresseTextField;
	private JTextField textFieldRecherche;
	
	public EditeurView(EditorController editorController) {
		this.editorController = editorController;
		initializeUI();

	}
	
	private void initializeUI() {
		
		setLayout(null);
		
		JLabel lblGestionDesEditeurs = new JLabel("Gestion des Editeurs");
		lblGestionDesEditeurs.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionDesEditeurs.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGestionDesEditeurs.setBounds(273, 11, 194, 25);
		add(lblGestionDesEditeurs);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 198, 668, 177);
		add(scrollPane);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Adresse");
		
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int selectedRow = table.getSelectedRow();
        		
        		if(selectedRow != -1) {
        			String id = tableModel.getValueAt(selectedRow, 0).toString();
        			String nom = tableModel.getValueAt(selectedRow, 1).toString();
        			String adresse = tableModel.getValueAt(selectedRow, 2).toString();
        			
        			idTextField.setText(id);
        			nomTextField.setText(nom);
        			adresseTextField.setText(adresse);
        		}
			}
		});
		scrollPane.setViewportView(table);
		
		idTextField = new JTextField();
		idTextField.setVisible(false);
		idTextField.setBounds(743, 15, 26, 20);
		add(idTextField);
		idTextField.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(48, 59, 84, 14);
		add(lblNom);
		
		nomTextField = new JTextField();
		nomTextField.setColumns(10);
		nomTextField.setBounds(48, 75, 143, 20);
		add(nomTextField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Adresse");
		lblNewLabel_1_1.setBounds(48, 104, 84, 14);
		add(lblNewLabel_1_1);
		
		adresseTextField = new JTextField();
		adresseTextField.setColumns(10);
		adresseTextField.setBounds(48, 120, 143, 20);
		add(adresseTextField);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEditor();
			}
		});
		btnAjouter.setBounds(201, 74, 120, 23);
		add(btnAjouter);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEditor();
			}
		});
		btnSupprimer.setBounds(201, 104, 120, 23);
		add(btnSupprimer);
		
		JButton btnMettreJour = new JButton("Mettre à Jour");
		btnMettreJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEditor();
			}
		});
		btnMettreJour.setBounds(201, 132, 120, 23);
		add(btnMettreJour);
		
		textFieldRecherche = new JTextField();
		textFieldRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				searchByName();
			}
		});
		textFieldRecherche.setColumns(10);
		textFieldRecherche.setBounds(48, 169, 203, 20);
		add(textFieldRecherche);
		
		JButton btnRechercherParNom = new JButton("Rechercher par Nom");
		btnRechercherParNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRechercherParNom.setBounds(261, 166, 173, 23);
		add(btnRechercherParNom);
		
		loadEditors();
	}
	
	private void searchByName() {
		String name = textFieldRecherche.getText();
		
		List<Editor> editors = editorController.searchByName(name);
		
		tableModel.setRowCount(0);
		
		for (Editor editor: editors) {
            tableModel.addRow(new Object[]{
                editor.getId(),
                editor.getNom(),
                editor.getAddress()
            });
        }
	}
	
	private void addEditor() {
		String nom = nomTextField.getText();
		String adresse = adresseTextField.getText();
		Editor editor = new Editor(nom, adresse);
		editorController.addEditor(editor);
		clearFields();
		loadEditors();
	}
	
	private void deleteEditor() {
		Integer id = Integer.parseInt(idTextField.getText());
		editorController.deleteEditor(id);
		clearFields();
		loadEditors();
	}
	
	private void updateEditor() {
		Integer id = Integer.parseInt(idTextField.getText());
		String nom = nomTextField.getText();
		String adresse = adresseTextField.getText();
		
		Editor editor = new Editor(id, nom, adresse);
		editorController.updateEditor(editor);
		clearFields();
		loadEditors();
	}
	
	private void loadEditors() {
		List<Editor> editors = editorController.getAllEditors();
		
		tableModel.setRowCount(0);
		
		for(Editor editor : editors) {
			tableModel.addRow(new Object[] {
					editor.getId(),
					editor.getNom(),
					editor.getAddress()
			});
		}
	}
	
	private void clearFields() {
		idTextField.setText("");
		nomTextField.setText("");
		adresseTextField.setText("");
	}

}
