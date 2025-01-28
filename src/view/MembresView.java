package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.MemberController;
import model.Member;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MembresView extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	
	private MemberController memberController;
	private JTable table;
	private JTextField nomTextField;
	private JTextField prenomTextField;
	private JTextField courrielTextField;
	private JTextField dateInscriptionTextField;
	private JTextField textFieldRecherche;
	
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
		scrollPane.setBounds(43, 209, 718, 194);
		add(scrollPane);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Id");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Prenom");
		tableModel.addColumn("Email");
		tableModel.addColumn("Date de Inscription");
		
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				
				if(selectedRow != -1) {
					String nom = tableModel.getValueAt(selectedRow, 1).toString();
					String prenom = tableModel.getValueAt(selectedRow, 2).toString();
					String email = tableModel.getValueAt(selectedRow, 3).toString();
					String dateInscription = tableModel.getValueAt(selectedRow, 4).toString();

					nomTextField.setText(nom);
					prenomTextField.setText(prenom);
					courrielTextField.setText(email);
					dateInscriptionTextField.setText(dateInscription);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Nom");
		lblNewLabel.setBounds(43, 53, 46, 14);
		add(lblNewLabel);
		
		nomTextField = new JTextField();
		nomTextField.setBounds(43, 72, 153, 20);
		add(nomTextField);
		nomTextField.setColumns(10);
		
		prenomTextField = new JTextField();
		prenomTextField.setColumns(10);
		prenomTextField.setBounds(233, 72, 153, 20);
		add(prenomTextField);
		
		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setBounds(233, 53, 75, 14);
		add(lblPrenom);
		
		JLabel lblCourriel = new JLabel("Courriel");
		lblCourriel.setBounds(43, 111, 75, 14);
		add(lblCourriel);
		
		courrielTextField = new JTextField();
		courrielTextField.setColumns(10);
		courrielTextField.setBounds(43, 130, 153, 20);
		add(courrielTextField);
		
		JLabel lblDateInscription = new JLabel("Date Inscription");
		lblDateInscription.setBounds(233, 111, 106, 14);
		add(lblDateInscription);
		
		dateInscriptionTextField = new JTextField();
		dateInscriptionTextField.setColumns(10);
		dateInscriptionTextField.setBounds(233, 130, 153, 20);
		add(dateInscriptionTextField);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nom = nomTextField.getText();
		            String prenom = prenomTextField.getText();
		            String email = courrielTextField.getText();
		            String dateStr = dateInscriptionTextField.getText();

		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            Date dateInscription;
					try {
						dateInscription = dateFormat.parse(dateStr);
						
			            Member newMember = new Member(nom, prenom, email, dateInscription);

			            memberController.addMember(newMember);

			            emptyFieldsAndUpdate();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
			}
		});
		btnAjouter.setBounds(410, 69, 100, 23);
		add(btnAjouter);
		
		JButton btnMiseAJour = new JButton("Mise à jour");
		btnMiseAJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String nom = nomTextField.getText();
				String prenom = prenomTextField.getText();
				String email = courrielTextField.getText();
				String dateStr = dateInscriptionTextField.getText();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date dateInscription;
				try {
					dateInscription = dateFormat.parse(dateStr);
					
		            Member memberUpdated = new Member(nom, prenom, email, dateInscription);
		            int id = (int) tableModel.getValueAt(selectedRow, 0);
		            memberController.updateMember(id, memberUpdated);

		            emptyFieldsAndUpdate();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnMiseAJour.setBounds(410, 102, 100, 23);
		add(btnMiseAJour);
		
		// I need to solve the problems related to foreign key constraint fails (`bibliotheque`.`emprunt`, CONSTRAINT `emprunt_ibfk_2` FOREIGN KEY (`ID_Membre`) REFERENCES `membre`
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				int id = (int) tableModel.getValueAt(selectedRow, 0);
				memberController.deleteMember(id);
				emptyFieldsAndUpdate();
			}
		});
		btnSupprimer.setBounds(410, 134, 100, 23);
		add(btnSupprimer);
		
		textFieldRecherche = new JTextField();
		textFieldRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				rechercherParCourriel();
			}
		});
		textFieldRecherche.setColumns(10);
		textFieldRecherche.setBounds(43, 178, 343, 20);
		add(textFieldRecherche);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emptyFieldsAndUpdate();
			}
		});
		btnRefresh.setBounds(410, 175, 100, 23);
		add(btnRefresh);
		
		JLabel lblRechercherParCourriel = new JLabel("Rechercher par Courriel");
		lblRechercherParCourriel.setBounds(43, 161, 343, 14);
		add(lblRechercherParCourriel);

		loadMembers();
	}
	
	private void rechercherParCourriel() {
		List<Member> membersSearched = memberController.findByEmail(textFieldRecherche.getText());
		
		tableModel.setRowCount(0);
		
		for (Member member : membersSearched) {
			tableModel.addRow(new Object[] {
					member.getIdMembre(),
					member.getNom(),
					member.getPrenom(),
					member.getEmail(),
					member.getDateInscription()
			});
		}
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
	
	public void emptyFieldsAndUpdate() {
		nomTextField.setText("");
		prenomTextField.setText("");
		courrielTextField.setText("");
		dateInscriptionTextField.setText("");
		textFieldRecherche.setText("");
		loadMembers();
	}
}
