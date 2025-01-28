package model;

import java.util.ArrayList;
import java.util.List;

public class Category implements CategoryComponent {

	private int id;
	private String name;
	private List<CategoryComponent> children;
	private int parentId;
	
	public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }
	
	@Override
    public void add(CategoryComponent component) {
        children.add(component);
    }

    @Override
    public void remove(CategoryComponent component) {
        children.remove(component);
    }

    public String getName() {
        return name;
    }

    public List<CategoryComponent> getChildren() {
        return children;
    }

    @Override
    public void display() {
        System.out.println("Category: " + name);
        for (CategoryComponent component : children) {
            component.display(); 
        }
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
