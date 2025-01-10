package model;

import java.util.ArrayList;
import java.util.List;

public class Category implements CategoryComponent {

	private int id;
	private String name;
	private List<CategoryComponent> children;
	
	public Category(int id, String name) {
        this.setId(id);
		this.name = name;
        this.children = new ArrayList<>();
    }
	
	public Category(String name) {
		this.name = name;
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
	
	

}
