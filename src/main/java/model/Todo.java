/**
 * 
 */
package model;

/**
 * 
 */
public class Todo {
	private int id;
	private String title;
	
	public Todo() {}
	public Todo(String title) {
		this.title = title;
	}
	
	//Getter & Setter
	public int getId() {return id;}
	
	public String getTitle() {return title;}
	
	public void setId(int id) {this.id =id;}
	
	public void setTitle(String title) {this.title = title;}
	
}
