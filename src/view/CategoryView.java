package view;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import controller.CategoryController;
import model.CategoryComponent;
import model.Category;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoryView extends JPanel {

    private static final long serialVersionUID = 1L;
    private CategoryController categoryController;
    private JTextField txtCategoryName;
    private JTree tree;
    private JComboBox<Category> comboParentCategories;
    private DefaultTreeModel treeModel;

    public CategoryView(CategoryController categoryController) {
        this.categoryController = categoryController;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);
        
        // Título
        JLabel lblTitle = new JLabel("Gestion des Catégories");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(150, 10, 300, 25);
        add(lblTitle);
        
        // Campo de texto para nome da categoria
        JLabel lblCategoryName = new JLabel("Nom de la Catégorie:");
        lblCategoryName.setBounds(20, 50, 150, 20);
        add(lblCategoryName);
        
        txtCategoryName = new JTextField();
        txtCategoryName.setBounds(20, 75, 200, 25);
        add(txtCategoryName);
        
        // Combo box para categorias pai
        JLabel lblParentCategory = new JLabel("Catégorie Parent:");
        lblParentCategory.setBounds(20, 115, 150, 20);
        add(lblParentCategory);
        
        comboParentCategories = new JComboBox<>();
        comboParentCategories.setBounds(20, 140, 200, 25);
        add(comboParentCategories);
        
        // Botão de adicionar
        JButton btnAdd = new JButton("Ajouter");
        btnAdd.setBounds(20, 180, 100, 25);
        add(btnAdd);
        
        // Árvore de categorias
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(250, 50, 300, 200);
        add(scrollPane);
        
        tree = new JTree();
        scrollPane.setViewportView(tree);
        
        // Carrega dados iniciais
        loadCategories();
        setupListeners(btnAdd);
    }

    private void loadCategories() {
        // Carrega árvore de categorias
        CategoryComponent rootCategory = categoryController.getRootCategory();
        DefaultMutableTreeNode rootNode = buildTree(rootCategory);
        treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
        
        // Carrega combo box
        comboParentCategories.removeAllItems();
        comboParentCategories.addItem(new Category(0, "Aucun Parent", -1)); // Item padrão
        for (Category cat : categoryController.getAllCategories()) {
            comboParentCategories.addItem(cat);
        }
    }

    private DefaultMutableTreeNode buildTree(CategoryComponent component) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(component);
        
        if (component instanceof Category) {
            Category category = (Category) component;
            for (CategoryComponent child : category.getChildren()) {
                node.add(buildTree(child));
            }
        }
        return node;
    }

    private void setupListeners(JButton btnAdd) {
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewCategory();
            }
        });
    }

    private void addNewCategory() {
        String categoryName = txtCategoryName.getText().trim();
        Category parentCategory = (Category) comboParentCategories.getSelectedItem();
        
        if (!categoryName.isEmpty() && parentCategory != null) {
            int parentId = parentCategory.getId() == 0 ? -1 : parentCategory.getId();
            
            categoryController.addCategory(categoryName, parentId);
            txtCategoryName.setText("");
            loadCategories(); // Atualiza a interface
        }
    }
}