package com.yushkev.onlinetraining.entity;

import com.yushkev.onlinetraining.entity.enumtype.UserRole;

public class User implements BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String login;
	private String password;
	private UserRole role;
	private String first_name;
	private String last_name;
	private String email;
	private Boolean active;
	private String avatar_path;
	
		
	public User() { 
		super();
		this.role = UserRole.GUEST; //By default not logged in users are Guests
	}
	
//	//constructor for getting user from database (without password) // XXX not used ATM
//	public User(Integer id, String login, UserRole role, String first_name, String last_name,
//			String email, Boolean isActive, String avatar_path) { 
//		super();
//		this.id = id;
//		this.login = login;
//		this.role = role;
//		this.first_name = first_name;
//		this.last_name = last_name;
//		this.email = email;
//		this.active = isActive;
//		this.avatar_path = avatar_path;
//	}
	
	//constructor for putting new User in DB
	public User(String login, String password, UserRole role, String first_name, String last_name,
			String email, Boolean isActive, String avatar_path) { 
		super();
		this.login = login;
		this.password = password;
		this.role = role;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.active = isActive;
		this.avatar_path = avatar_path;
	}
	
	//constructor without password to store in session
	public User(String login, UserRole role, String first_name, String last_name,
			String email, Boolean isActive, String avatar_path) { 
		super();
		this.login = login;
		this.role = role;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.active = isActive;
		this.avatar_path = avatar_path;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean isActive) {
		this.active = isActive;
	}

	public String getAvatar_path() {
		return avatar_path;
	}



	public void setAvatar_path(String avatar_path) {
		this.avatar_path = avatar_path;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
