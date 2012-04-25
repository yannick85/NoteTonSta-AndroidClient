package entity;

import java.util.Date;

public class Intervention {

	private Long id;

	private String subject;
	
	private String description;
	
	private Date beginDate;
	
	private Date endDate;
	
	private Long campus;
	
	private Long speaker;
	
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCampus() {
		return campus;
	}

	public void setCampus(Long campus) {
		this.campus = campus;
	}

	public Long getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Long speakerId) {
		this.speaker = speakerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String toString(){
		return subject;
	}
}
