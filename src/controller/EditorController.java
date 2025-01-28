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
	
	public void addEditor(Editor editor) {
		editorDAO.addEditor(editor);
	}
	
	public void deleteEditor(int id) {
		editorDAO.deleteEditor(id);
	}
	
	public void updateEditor(Editor editor) {
		editorDAO.updateEditor(editor);
	}
	
	public List<Editor> searchByName(String name) {
		return editorDAO.searchByName(name);
	}
	
}
