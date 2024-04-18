
public class Account {
	private String id;
	private String type;
	private double total;
	
	public Account(String id, String type, double total) {
		this.id = id;
		this.type = type;
		this.total = total;
	}
	
	public String getID() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void addToTotal(double amount) {
		total = total + amount;
	}
	
	public void removeFromTotal(double amount) {
		total = total - amount;
	}
	
	public String data() {
		return "";
	}
}
