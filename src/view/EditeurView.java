package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.EditorController;
import model.Editor;

import javax.swing.JScrollPane;

public class EditeurView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private EditorController editorController;
	
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
		scrollPane.setBounds(48, 198, 721, 177);
		add(scrollPane);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Adresse");
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
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

}
