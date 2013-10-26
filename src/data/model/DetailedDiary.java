package data.model;

import data.DiaryManager;
import util.Utility;

public class DetailedDiary {
	private int id;
	private String title;
	private String tag;
	private long time;
	private String contentURL;
	private String voiceURL;
	private String username;
	private String shareOrPrivate;
	
	public DetailedDiary() {
		this.id = Utility.generateRandomID();
		this.title = "Title";
		this.tag = "Tag";
		this.time = Utility.getTime();
		this.contentURL = "./diary/content/" + id;
		this.voiceURL = "";
		this.username = DiaryManager.getInstance().getUser().getUsername();
		this.shareOrPrivate = "private";
	}
	
	public DetailedDiary(int id, String title, String tag, long time, String content, String voiceURL, String username, String shareOrPrivate) {
		this.id = id;
		this.title = title;
		this.tag = tag;
		this.time = time;
		this.contentURL = content;
		this.voiceURL = voiceURL;
		this.username = username;
		this.shareOrPrivate = shareOrPrivate;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShareOrPrivate() {
		return shareOrPrivate;
	}

	public void setShareOrPrivate(String shareOrPrivate) {
		this.shareOrPrivate = shareOrPrivate;
	}

	public boolean hasVoice() {
		if (voiceURL == null)
			return false;
		if (voiceURL.equals(""))
			return false;
		return true;
	}
}
