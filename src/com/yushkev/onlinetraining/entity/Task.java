package com.yushkev.onlinetraining.entity;

import java.util.GregorianCalendar;

public class Task implements BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private Course course;
	private GregorianCalendar endDate;
	private String description;
	
		
	public Task() {
		super();
	}

	public Task(Integer id, String title, Course course, String description) {
		super();
		this.id = id;
		this.title = title;
		this.course = course;
		this.description = description;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public GregorianCalendar getEndDate() {
		return endDate;
	}


	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	

	
	
}
