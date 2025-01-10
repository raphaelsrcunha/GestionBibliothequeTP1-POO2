package controller;

import java.util.List;

import model.Category;
import model.CategoryDAO;

public class CategoryController {
	
	private CategoryDAO categoryDAO;
	
	public CategoryController(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	public List<Category> getAllCategories() {
		return categoryDAO.getAllCategories();
	}

}
