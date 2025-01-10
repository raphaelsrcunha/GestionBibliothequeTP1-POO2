package controller;

import java.util.List;

import model.Editor;
import model.EditorDAO;

public class EditorController {

	private EditorDAO editorDAO;
	
	public EditorController(EditorDAO editorDAO) {
		this.editorDAO = editorDAO;
	}
	
	public List<Editor> getAllEditors() {
		return editorDAO.getAllEditors();
	}
	
}
