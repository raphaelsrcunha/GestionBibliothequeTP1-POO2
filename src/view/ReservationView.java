package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import controller.ReservationController;
import controller.BookController;
import controller.MemberController;
import model.Reservation;
import model.Book;
import model.Member;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Book> bookComboBox;
    private JComboBox<Member> memberComboBox;
    private JTextField dateReservationTextField;
    
    private ReservationController reservationController;
    private BookController bookController;
    private MemberController memberController;

    public ReservationView(ReservationController reservationController, 
                          BookController bookController, 
                          MemberController memberController) {
        
        this.reservationController = reservationController;
        this.bookController = bookController;
        this.memberController = memberController;
        
        initializeUI();
        loadReservations();
        loadBooks();
        loadMembers();
    }

    private void initializeUI() {
        setLayout(null);
        
        // Título
        JLabel lblTitle = new JLabel("Gestion des Réservations");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(250, 11, 300, 25);
        add(lblTitle);

        // Combo Box Livres
        bookComboBox = new JComboBox<>();
        bookComboBox.setBounds(35, 60, 200, 25);
        add(bookComboBox);
        
        JLabel lblBook = new JLabel("Livre");
        lblBook.setBounds(35, 45, 200, 14);
        add(lblBook);

        // Combo Box Membres
        memberComboBox = new JComboBox<>();
        memberComboBox.setBounds(35, 110, 200, 25);
        add(memberComboBox);
        
        JLabel lblMember = new JLabel("Membre");
        lblMember.setBounds(35, 95, 200, 14);
        add(lblMember);

        // Date Reservation
        dateReservationTextField = new JTextField();
        dateReservationTextField.setBounds(35, 160, 200, 25);
        add(dateReservationTextField);
        
        JLabel lblDate = new JLabel("Date Réservation");
        lblDate.setBounds(35, 145, 200, 14);
        add(lblDate);

        // Boutons
        JButton btnAdd = new JButton("Ajouter");
        btnAdd.setBounds(260, 60, 120, 25);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addReservation();
            }
        });
        add(btnAdd);

        JButton btnDelete = new JButton("Supprimer");
        btnDelete.setBounds(260, 110, 120, 25);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteReservation();
            }
        });
        add(btnDelete);

        // Table
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 200, 730, 177);
        add(scrollPane);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nom du Livre");
        tableModel.addColumn("Prénom Membre");
        tableModel.addColumn("Date Réservation");

        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1) {
                    String bookTitle = (String) tableModel.getValueAt(selectedRow, 0);
                    String memberPrenom = (String) tableModel.getValueAt(selectedRow, 1);
                    
                    Book book = bookController.getBookByTitle(bookTitle);
                    Member member = memberController.getMemberByPrenom(memberPrenom);
                    
                    if(book != null) bookComboBox.setSelectedItem(book);
                    if(member != null) memberComboBox.setSelectedItem(member);
                }
            }
        });
        scrollPane.setViewportView(table);
    }

    private void loadReservations() {
        List<Reservation> reservations = reservationController.getAllReservations();
        tableModel.setRowCount(0);

        for (Reservation res : reservations) {
            Book book = bookController.getBook(res.getIdLivre());
            Member member = memberController.getMember(res.getIdMembre());
            
            String bookTitle = (book != null) ? book.getTitre() : "Inconnu";
            String memberPrenom = (member != null) ? member.getPrenom() : "Inconnu";

            tableModel.addRow(new Object[]{
                bookTitle,
                memberPrenom,
                res.getDateReservation()
            });
        }
    }

    private void loadBooks() {
        List<Book> books = bookController.getAllBooks();
        bookComboBox.removeAllItems();
        bookComboBox.addItem(null);
        for (Book book : books) {
            bookComboBox.addItem(book);
        }
    }

    private void loadMembers() {
        List<Member> members = memberController.getAllMembers();
        memberComboBox.removeAllItems();
        memberComboBox.addItem(null);
        for (Member member : members) {
            memberComboBox.addItem(member);
        }
    }

    private void addReservation() {
        Book selectedBook = (Book) bookComboBox.getSelectedItem();
        Member selectedMember = (Member) memberComboBox.getSelectedItem();

        if (selectedBook == null || selectedMember == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un livre et un membre", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reservation newReservation = new Reservation(
            selectedBook.getID_Livre(),
            selectedMember.getIdMembre(),
            new Date()
        );

        if (reservationController.addReservation(newReservation)) {
            loadReservations();
            JOptionPane.showMessageDialog(this, "Réservation ajoutée!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de l'ajout", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteReservation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une réservation", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookTitle = (String) tableModel.getValueAt(selectedRow, 0);
        String memberPrenom = (String) tableModel.getValueAt(selectedRow, 1);

        Book book = bookController.getBookByTitle(bookTitle);
        Member member = memberController.getMemberByPrenom(memberPrenom);

        if(book == null || member == null) {
            JOptionPane.showMessageDialog(this,
                "Impossible de trouver la réservation",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (reservationController.deleteReservation(book.getID_Livre(), member.getIdMembre())) {
            loadReservations();
            JOptionPane.showMessageDialog(this, "Réservation supprimée!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la suppression", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}