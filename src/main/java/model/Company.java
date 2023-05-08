package model;

public class Company {
	private int id;
	private String name;
	private String role;
	private String start;
	private String end;
	private String user_id;
	
	
	public Company() {
		this(0);
	}
	
	public Company(int id) {
		this.id = id;
		setName("");
		setRole("");
		setStart("");
		setEnd("");
		setUser_id("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
