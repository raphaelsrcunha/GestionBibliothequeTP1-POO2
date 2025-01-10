package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.MemberController;
import model.Member;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MembresView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	
	private MemberController memberController;
	private JTable table;
	
	public MembresView(MemberController memberController) {
		this.memberController = memberController;
		initializeUI();
	}
	
	public void initializeUI() {
		setLayout(null);
		
		JLabel lblGestionDesMembres = new JLabel("Gestion des Membres");
		lblGestionDesMembres.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionDesMembres.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGestionDesMembres.setBounds(278, 11, 182, 25);
		add(lblGestionDesMembres);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 146, 718, 221);
		add(scrollPane);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Id");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Prenom");
		tableModel.addColumn("Email");
		tableModel.addColumn("Date de Inscription");
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		loadMembers();
	}
	
	public void loadMembers() {
		List<Member> members = memberController.getAllMembers();
		tableModel.setRowCount(0);
		
		for(Member member : members) {
			tableModel.addRow(new Object[] {
					member.getIdMembre(),
					member.getNom(),
					member.getPrenom(),
					member.getEmail(),
					member.getDateInscription()
			});
		}
	}

}
