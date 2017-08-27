package shop.entity;

public class Product {

	// Pola produktu
	private int id;
	private String name;
	private String category;
	private String description;
	private int evaluation;
	private double price;
	private int quantity;

	// Getery i setery
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getCategory() {
		return this.category;
	}

	public String getDescription() {
		return this.description;
	}

	public int getEvaluation() {
		return this.evaluation;
	}

	public double getPrice() {
		return this.price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantitu(int quantity) {
		this.quantity = quantity;
	}

	// Konstruktory
	public Product() {

	}

	public Product(String name, String category, double price, int quantity) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = "No description";
		this.evaluation = 0;
	}

	public Product(String name, String category, String description, int evaluation, double price, int quantity) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.evaluation = evaluation;
	}

	public Product(int id, String name, String category, String description, int evaluation, double price,
			int quantity) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.evaluation = evaluation;
	}

	// toString
	@Override
	public String toString() {
		return String.format(
				"Id: %d\nName: %s\nCategory: %s\nDescription: %s\nEvaluation: %d/5\nPrice: %.2fPLN\nQuantity: %d",
				this.id, this.name, this.category, this.description, this.evaluation, this.price, this.quantity);
	}

}
