package controller;

import java.util.List;

import model.Category;
import model.CategoryComponent;
import model.CategoryDAO;

public class CategoryController {
	
	private CategoryDAO categoryDAO;
	
	public CategoryController(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	public List<Category> getAllCategories() {
		return categoryDAO.getAllCategories();
	}
	
	 public CategoryComponent getRootCategory() {
	        return categoryDAO.getRootCategory();
	    }
	 
	public void addCategory(String name, int parentId) {
	    categoryDAO.addCategory(name, parentId);
	}

}
