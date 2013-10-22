package data.model;

import util.Utility;

public class SimpleDiary {

	private int id;
	private String title;
	private String tag;
	private long time;
	private boolean hasVoice;
	
	public SimpleDiary(int id, String title, String tag, long time, boolean hasVoice) {
		this.id = id;
		this.title = title;
		this.tag = tag;
		this.time = time;
		this.hasVoice = hasVoice;
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

	public boolean isHasVoice() {
		return hasVoice;
	}

	public void setHasVoice(boolean hasVoice) {
		this.hasVoice = hasVoice;
	}
}
