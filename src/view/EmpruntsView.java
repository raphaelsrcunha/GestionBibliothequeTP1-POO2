package view;

import java.awt.Font;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.BorrowCommand;
import model.CommandManager;
import model.ReturnCommand;
import controller.BookController;
import controller.EmpruntController;
import controller.MemberController;
import controller.ReservationController;
import model.Book;
import model.Emprunt;
import model.EmpruntDTO;
import model.Member;
import model.Reservation;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmpruntsView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Book> booksComboBox;
    private JComboBox<Member> membersComboBox;
    private JTextField dateEmpruntTextField;
    private JTextField dateRetourPrevueTextField;

    private EmpruntController empruntController;
    private MemberController memberController;
    private BookController bookController;
    private CommandManager commandManager;
    private ReservationController reservationController;

    public EmpruntsView(EmpruntController empruntController, 
                       MemberController memberController, 
                       BookController bookController, 
                       CommandManager commandManager,
                       ReservationController reservationController) {
        
        this.empruntController = empruntController;
        this.memberController = memberController;
        this.bookController = bookController;
        this.commandManager = commandManager;
        this.reservationController = reservationController;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);

        // Título
        JLabel lblTitle = new JLabel("Gestion des Emprunts");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(284, 11, 189, 25);
        add(lblTitle);

        // Tabela
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 206, 716, 191);
        add(scrollPane);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Emprunt");
        tableModel.addColumn("ID Livre");
        tableModel.addColumn("Titre Livre");
        tableModel.addColumn("ID Membre");
        tableModel.addColumn("Prenom");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Date Emprunt");
        tableModel.addColumn("Date Retour Prevue");
        tableModel.addColumn("Date Retour Effective");

        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // Comboboxes
        booksComboBox = new JComboBox<>();
        booksComboBox.setBounds(46, 63, 176, 25);
        add(booksComboBox);

        membersComboBox = new JComboBox<>();
        membersComboBox.setBounds(46, 125, 176, 25);
        add(membersComboBox);

        // Labels
        JLabel lblBook = new JLabel("Livre");
        lblBook.setBounds(46, 45, 176, 14);
        add(lblBook);

        JLabel lblMember = new JLabel("Membre");
        lblMember.setBounds(46, 110, 176, 14);
        add(lblMember);

        // Campos de data
        dateEmpruntTextField = new JTextField();
        dateEmpruntTextField.setBounds(248, 65, 133, 20);
        add(dateEmpruntTextField);
        dateEmpruntTextField.setColumns(10);

        JLabel lblDateEmprunt = new JLabel("Date Emprunt");
        lblDateEmprunt.setBounds(248, 47, 133, 14);
        add(lblDateEmprunt);

        dateRetourPrevueTextField = new JTextField();
        dateRetourPrevueTextField.setColumns(10);
        dateRetourPrevueTextField.setBounds(248, 114, 133, 20);
        add(dateRetourPrevueTextField);

        JLabel lblDateRetourPrevue = new JLabel("Date Retour Prevue");
        lblDateRetourPrevue.setBounds(248, 96, 133, 14);
        add(lblDateRetourPrevue);

        // Botões
        JButton empruntBtn = new JButton("Emprunt");
        empruntBtn.setBounds(409, 64, 133, 23);
        empruntBtn.addActionListener(e -> performBorrow());
        add(empruntBtn);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(409, 106, 133, 23);
        btnRetour.addActionListener(e -> performReturn());
        add(btnRetour);

        JButton btnUndo = new JButton("Undo");
        btnUndo.setBounds(409, 145, 133, 23);
        btnUndo.addActionListener(e -> {
            commandManager.undoLastCommand();
            loadEmprunts();
        });
        add(btnUndo);

        loadEmprunts();
        loadMembers();
        loadBooks();
    }

    private void performBorrow() {
        Book selectedBook = (Book) booksComboBox.getSelectedItem();
        Member selectedMember = (Member) membersComboBox.getSelectedItem();

        if (selectedBook == null || selectedMember == null) {
            JOptionPane.showMessageDialog(this, 
                "Sélectionnez un livre et un membre", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Date dateEmprunt = new Date();
            Date dateRetourPrevue = new Date(dateEmprunt.getTime() + 7L * 24 * 60 * 60 * 1000);

            Emprunt emprunt = new Emprunt(
                selectedBook.getID_Livre(),
                selectedMember.getIdMembre(),
                dateEmprunt,
                dateRetourPrevue,
                null
            );

            BorrowCommand borrowCommand = new BorrowCommand(empruntController, emprunt);
            commandManager.executeCommand(borrowCommand);
            loadEmprunts();
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de l'emprunt", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performReturn() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Sélectionnez un emprunt à retourner", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int empruntId = (int) tableModel.getValueAt(selectedRow, 0);
        int bookId = (int) tableModel.getValueAt(selectedRow, 1);

        // Notificar reservas
        List<Reservation> reservations = reservationController.getReservationsByBook(bookId);
        Book book = bookController.getBook(bookId);

        if (!reservations.isEmpty()) {
            for (Reservation res : reservations) {
                Member member = memberController.getMember(res.getIdMembre());
                
                String message = String.format(
                    "<html><body style='width: 300px; padding: 10px; text-align: center'>" +
                    "Le livre <strong>%s</strong> est maintenant disponible.<br>" +
                    "Réservé par: %s %s" +
                    "</body></html>",
                    book.getTitre(),
                    member.getPrenom(),
                    member.getNom()
                );

                JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Livre Disponible",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            reservationController.processReturnAndNotify(bookId);
        }

        // Executar retour
        ReturnCommand returnCommand = new ReturnCommand(empruntController, empruntId);
        commandManager.executeCommand(returnCommand);
        loadEmprunts();
        clearFields();
    }

    private void loadEmprunts() {
        tableModel.setRowCount(0);
        List<EmpruntDTO> emprunts = empruntController.getAllEmpruntsDTO();

        for (EmpruntDTO emp : emprunts) {
            tableModel.addRow(new Object[]{
                emp.getIdEmprunt(),
                emp.getIdLivre(),
                emp.getTitreLivre(),
                emp.getIdMembre(),
                emp.getPrenomMembre(),
                emp.getNomMembre(),
                emp.getDateEmprunt(),
                emp.getDateRetourPrevue(),
                emp.getDateRetourEffective()
            });
        }
    }

    private void loadMembers() {
        membersComboBox.removeAllItems();
        membersComboBox.addItem(null);
        List<Member> members = memberController.getAllMembers();
        for (Member m : members) {
            membersComboBox.addItem(m);
        }
    }

    private void loadBooks() {
        booksComboBox.removeAllItems();
        booksComboBox.addItem(null);
        List<Book> books = bookController.getAllBooks();
        for (Book b : books) {
            booksComboBox.addItem(b);
        }
    }
    
    private void clearFields() {
    	booksComboBox.setSelectedIndex(0);
    	membersComboBox.setSelectedIndex(0);
    	dateEmpruntTextField.setText("");
    	dateRetourPrevueTextField.setText("");
    }
}