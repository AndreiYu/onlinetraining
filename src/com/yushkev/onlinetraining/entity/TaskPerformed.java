package com.yushkev.onlinetraining.entity;

import java.util.GregorianCalendar;

import com.yushkev.onlinetraining.entity.enumtype.TaskPerformedStatus;

public class TaskPerformed extends Task implements BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer taskPerformedId;
	private User student;
	private TaskPerformedStatus status;
	private Integer mark;
	private GregorianCalendar performanceDate;
	private String teacherComment;
	private String userComment;
	
	
	
	public TaskPerformed() {
		super();
	}

	public TaskPerformed(Integer id, String title, Course course, String description, 
			Integer taskPerformedID, TaskPerformedStatus status) {
		super(id, title, course, description);
		this.status = status;
		this.taskPerformedId = taskPerformedID;
	}

	public Integer getTaskPerformedId() {
		return taskPerformedId;
	}

	public void setTaskPerformedId(Integer taskPerformedId) {
		this.taskPerformedId = taskPerformedId;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public TaskPerformedStatus getStatus() {
		return status;
	}

	public void setStatus(TaskPerformedStatus status) {
		this.status = status;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public GregorianCalendar getPerformanceDate() {
		return performanceDate;
	}

	public void setPerformanceDate(GregorianCalendar performanceDate) {
		this.performanceDate = performanceDate;
	}

	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((taskPerformedId == null) ? 0 : taskPerformedId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskPerformed other = (TaskPerformed) obj;
		if (taskPerformedId == null) {
			if (other.taskPerformedId != null)
				return false;
		} else if (!taskPerformedId.equals(other.taskPerformedId))
			return false;
		return true;
	}
	
	
	
	
	
	
}
