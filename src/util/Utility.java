package util;

import java.awt.Color;
import java.util.Calendar;

public class Utility {

	public static final String SQL_URL = "jdbc:sqlite:./diary/lifenote.db";
	public static final String SQL_USER = "lifenote";
	public static final String SQL_PASSWORD = "123ewq";

	public static final String ICON = "./res/icon/1.png";
	public static final String TITLE = "LifeNote";

	public static final String START_FRAME_BACKGROUND = "./res/background/1.jpg";
	public static final String START_FRAME_INFO = "LifeNote is starting ";
	public static final String TITLE_FONT = "Segoe Script";

	public static final String LIST_FRAME_BACKGROUND = "./res/background/1.jpg";
	public static final String LIST_FRAME_CREATE = "./res/icon/4.png";
	public static final String LIST_FRAME_SEARCH = "./res/icon/5.png";
	public static final String LIST_FRAME_EXIT = "./res/icon/6.png";
	public static final String LIST_FRAME_REFRESH = "./res/icon/17.png";
	public static final String LIST_FRAME_FONT = "Î¢ÈíÑÅºÚ";
	public static final Color LIST_FRAME_FONT_COLOR = new Color(0, 0, 56);

	public static final String LIST_ELEMENT_HAS_IMAGE = "./res/icon/2.png";
	public static final String LIST_ELEMENT_HAS_VOICE = "./res/icon/3.png";
	public static final String LIST_ELEMENT_FONT = "Î¢ÈíÑÅºÚ";
	public static final Color LIST_ELEMENT_BACKGROUND_COLOR = new Color(149, 165, 166);

	public static final String DETAIL_FRAME_BACKGROUND = "./res/background/2.jpg";
	public static final String DETAIL_FRAME_FONT = "Î¢ÈíÑÅºÚ";
	public static final Color DETAIL_FRAME_FONT_COLOR = new Color(0, 0, 56);
	public static final String DETAIL_FRAME_RECORD = "./res/icon/7.png";
	public static final String DETAIL_FRAME_PLAY = "./res/icon/8.png";
	public static final String DETAIL_FRAME_STOP = "./res/icon/9.png";
	public static final String DETAIL_FRAME_EDIT = "./res/icon/10.png";
	public static final String DETAIL_FRAME_DELETE = "./res/icon/11.png";
	public static final String DETAIL_FRAME_BACK = "./res/icon/12.png";
	public static final String DETAIL_FRAME_INSERT = "./res/icon/13.png";
	public static final String DETAIL_FRAME_VOICEOFF = "./res/icon/14.png";
	public static final String DETAIL_FRAME_SAVE = "./res/icon/15.png";
	public static final String DETAIL_FRAME_CANCEL = "./res/icon/16.png";

	public static int generateRandomID() {
		int id = (int) (Integer.MAX_VALUE * Math.random());
		return id;
	}
	
	public static String formatTime(long time) {
		String s = "" + time;
		s = s.substring(0, 4) + "/" + s.substring(4, 6) + "/" + s.substring(6, 8) + " " + s.substring(8, 10) + ":" + s.substring(10, 12);
		return s;
	}
	
	public static long restoreFormattedTime(String time) {
		if (time.equals("NULL"))
			return -1;
		String s = new String("");
		s += time;
		s = s.replace("/", "");
		s = s.replace(" ", "");
		s = s.replace(":", "");
		return Long.valueOf(s).longValue();
	}
	
	public static long getTime() {
		Calendar c = Calendar.getInstance();
		long l = 0L;
		l += c.get(Calendar.YEAR) * 100000000L;
		l += (c.get(Calendar.MONTH) + 1) * 1000000L;
		l += c.get(Calendar.DATE) * 10000L;
		l += c.get(Calendar.HOUR_OF_DAY) * 100L;
		l += c.get(Calendar.MINUTE) * 1L;
		return l;
	}
	
	public static void main(String args[]) {
		System.out.println(getTime());
		System.out.println(formatTime(getTime()));
	}
}
