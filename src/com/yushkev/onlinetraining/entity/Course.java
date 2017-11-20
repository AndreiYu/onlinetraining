package com.yushkev.onlinetraining.entity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import com.yushkev.onlinetraining.entity.enumtype.CourseStatus;
import com.yushkev.onlinetraining.validator.DataValidator;

public class Course implements BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer lecturerId;
	private Integer typeId;
	private CourseStatus status;
	private String title;
	private GregorianCalendar startDate; //Timestamp sTimestamp;
	private GregorianCalendar endDate;	//Timestamp sTimestamp;
	private String description;	//Timestamp sTimestamp;
	private Double certificatePrice;
	private String avatar_path;
	private List<Task> tasks;
	private List<CourseEnrolment> enrolments;
	
	
	public Course() {
		super();
	}

	//constructor for putting new Course in DB
	public Course(Integer lecturerId, Integer typeId, CourseStatus status, String title,
			GregorianCalendar startDate, GregorianCalendar endDate, String description, Double certificatePrice,
			String avatar_path) {
		super();
		this.lecturerId = lecturerId;
		this.typeId = typeId;
		this.status = status;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.certificatePrice = certificatePrice;
		this.avatar_path = avatar_path;
	}
	
	//constructor for updating Course in DB
	public Course(Integer lecturerId, Integer typeId, CourseStatus status, String title,
			GregorianCalendar startDate, GregorianCalendar endDate, String description, Double certificatePrice,
			String avatar_path, Integer id) {
		super();
		this.lecturerId = lecturerId;
		this.typeId = typeId;
		this.status = status;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.certificatePrice = certificatePrice;
		this.avatar_path = avatar_path;
		this.id = id;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getLecturerId() {
		return lecturerId;
	}


	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}


	public Integer getTypeId() {
		return typeId;
	}


	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	public CourseStatus getStatus() {
		return status;
	}


	public void setStatus(CourseStatus status) {
		this.status = status;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public GregorianCalendar getStartDate() {
		return startDate;
	}

	public String getStartDateString() {
		SimpleDateFormat format = new SimpleDateFormat(DataValidator.DATE_SIMPLEFORMAT);
		return (startDate != null) ? format.format(startDate.getTime()) : null;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}


	public GregorianCalendar getEndDate() {
		return endDate;
	}
	
	public String getEndDateString() {
		SimpleDateFormat format = new SimpleDateFormat(DataValidator.DATE_SIMPLEFORMAT);
		return (endDate != null) ? format.format(endDate.getTime()) : null;
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

	public Double getCertificatePrice() {
		return certificatePrice;
	}


	public void setCertificatePrice(Double certificatePrice) {
		this.certificatePrice = certificatePrice;
	}

	public List<Task> getTasks() {
		return tasks;
	}


	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}


	public List<CourseEnrolment> getEnrolments() {
		return enrolments;
	}


	public void setEnrolments(List<CourseEnrolment> enrolments) {
		this.enrolments = enrolments;
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
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
	
}
