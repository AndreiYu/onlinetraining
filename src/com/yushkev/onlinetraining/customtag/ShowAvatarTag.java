package com.yushkev.onlinetraining.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

/**
 * Tag show user avatars or course image
 */
public class ShowAvatarTag extends TagSupport{

	private static final long serialVersionUID = 1L;

	private User user;
	private Integer courseId;
	private String cssClass;
	
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspTagException {
		
		String url = ((HttpServletRequest)pageContext.getRequest()).getRequestURL().toString();
		String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
		String avatarPath = ConfigurationManager.getProperty("path.dir.image").concat("/");

		if (courseId == null) {

			User user = ((User) pageContext.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER));
			avatarPath += user.getAvatar_path();
			avatarPath = avatarPath.contains(GeneralConstant.AVATAR_USER_IMG) ? avatarPath : avatarPath.concat(GeneralConstant.DEFAULT_IMG_NAME_USER);
		}
		else {
			/*check single course in request and session scope and in allCourses list*/
			Course course = (Course) pageContext.getRequest().getAttribute(GeneralConstant.REQUEST_ATTR_COURSE);
			if (course == null) {
				course = (Course) pageContext.getSession().getAttribute(GeneralConstant.SESSION_ATTR_COURSE);			
			}
			if (course == null) {
				course = (((List<Course>) pageContext.getRequest().getAttribute(GeneralConstant.REQUEST_ATTR_ALL_COURSES))
					.stream().filter(a->a.getId().equals(courseId)).findFirst().get());
			}
			avatarPath += course.getAvatar_path();
			avatarPath = avatarPath.contains(GeneralConstant.AVATAR_COURSE_IMG) ? avatarPath : avatarPath.concat(GeneralConstant.DEFAULT_IMG_NAME_COURSE);
		}
/*			convert real filePath to web-related*/
			avatarPath = url.substring(0, url.indexOf(contextPath) + contextPath.length() + 1) + avatarPath;
	
			JspWriter out = pageContext.getOut();
			try {
				out.write("<img ");
				if (cssClass != null) {
					out.write("class = \"");
					out.write(cssClass + "\"");
				}
				out.write(" src= \"" + avatarPath);
				out.write("\" alt=\"");
				out.write("Avatar image\"");
				out.write(">");
			} catch (IOException e) {
				throw new JspTagException(e);
			}

		return SKIP_BODY;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	

	
}
