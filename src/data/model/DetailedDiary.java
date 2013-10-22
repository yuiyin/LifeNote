package data.model;

import util.Utility;

public class DetailedDiary {
	private int id;
	private String title;
	private String tag;
	private long time;
	private String contentURL;
	private String voiceURL;
	
	public DetailedDiary() {
		this.id = Utility.generateRandomID();
		this.title = "Title";
		this.tag = "Tag";
		this.time = Utility.getTime();
		this.contentURL = "./diary/content/" + id;
		this.voiceURL = "";
	}
	
	public DetailedDiary(int id, String title, String tag, long time, String content, String voiceURL) {
		this.id = id;
		this.title = title;
		this.tag = tag;
		this.time = time;
		this.contentURL = content;
		this.voiceURL = voiceURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getTime() {
		return time;
	}
	
	public String getTimeString() {
		return Utility.formatTime(time);
	}
	public void setTime(long time) {
		this.time = time;
	}

	public String getContentURL() {
		return contentURL;
	}

	public void setContentURL(String content) {
		this.contentURL = content;
	}

	public String getVoiceURL() {
		return voiceURL;
	}

	public void setVoiceURL(String voiceURL) {
		this.voiceURL = voiceURL;
	}
	
	public boolean hasVoice() {
		if (voiceURL == null)
			return false;
		if (voiceURL.equals(""))
			return false;
		return true;
	}
}
