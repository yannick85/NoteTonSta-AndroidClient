package entity;

public class Mark {

	private Long id;
	
	private int idBooster;
	
	private int speakerKnowledge;
	
	private int speakerTeaching;
	
	private int speakerAnswers;
	
	private int slideContent;
	
	private int slideFormat;
	
	private int slideExample;
	
	private long interventionId;
	
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSpeakerKnowledge() {
		return speakerKnowledge;
	}

	public void setSpeakerKnowledge(int speakerKnowledge) {
		this.speakerKnowledge = speakerKnowledge;
	}

	public int getSpeakerTeaching() {
		return speakerTeaching;
	}

	public void setSpeakerTeaching(int speakerTeaching) {
		this.speakerTeaching = speakerTeaching;
	}

	public int getSpeakerAnswers() {
		return speakerAnswers;
	}

	public void setSpeakerAnswers(int speakerAnswers) {
		this.speakerAnswers = speakerAnswers;
	}

	public int getSlideContent() {
		return slideContent;
	}

	public void setSlideContent(int slideContent) {
		this.slideContent = slideContent;
	}

	public int getSlideFormat() {
		return slideFormat;
	}

	public void setSlideFormat(int slideFormat) {
		this.slideFormat = slideFormat;
	}

	public int getSlideExample() {
		return slideExample;
	}

	public void setSlideExample(int slideExample) {
		this.slideExample = slideExample;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIdBooster() {
		return idBooster;
	}

	public void setIdBooster(int idBooster) {
		this.idBooster = idBooster;
	}

	public long getInterventionId() {
		return interventionId;
	}

	public void setInterventionId(long interventionId) {
		this.interventionId = interventionId;
	}
}
