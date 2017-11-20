package com.yushkev.onlinetraining.entity;

import java.util.List;

import com.yushkev.onlinetraining.entity.enumtype.CourseEnrolmentStatus;

public class CourseEnrolment implements BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User student;
	private Course course;
	private CourseEnrolmentStatus status;
	private Integer finalMark;
	private String finalComment;
	private boolean certificateObtained;
	private List<TaskPerformed> tasksPerformed; //TODO:Delete if not needed
	
	public CourseEnrolment() {
		super();
	}

	public CourseEnrolment(User student, Course course, CourseEnrolmentStatus status) {
		super();
		this.student = student;
		this.course = course;
		this.status = status;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseEnrolmentStatus getStatus() {
		return status;
	}

	public void setStatus(CourseEnrolmentStatus status) {
		this.status = status;
	}

	public Integer getFinalMark() {
		return finalMark;
	}

	public void setFinalMark(Integer finalMark) {
		this.finalMark = finalMark;
	}

	public String getFinalComment() {
		return finalComment;
	}

	public void setFinalComment(String finalComment) {
		this.finalComment = finalComment;
	}

	public boolean isCertificateObtained() {
		return certificateObtained;
	}

	public void setCertificateObtained(boolean certificateObtained) {
		this.certificateObtained = certificateObtained;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result + ((tasksPerformed == null) ? 0 : tasksPerformed.hashCode());
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
		CourseEnrolment other = (CourseEnrolment) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (tasksPerformed == null) {
			if (other.tasksPerformed != null)
				return false;
		} else if (!tasksPerformed.equals(other.tasksPerformed))
			return false;
		return true;
	}
	
	

}
