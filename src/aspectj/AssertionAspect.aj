package aspectj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import data.model.DetailedDiary;
import util.Utility;
import util.SortType;
import data.DiaryManager;

public aspect AssertionAspect {
	private boolean isTimeIllegal(String time) {
		if (time == null) {
			return false;
		}
		if (time.equals("NULL")) {
			return true;
		}
		DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			fmt.parse(time);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private boolean checkRegister(String pass) {
		String patternStr = "(\\w|\\p{Graph}){6,16}";
		boolean b = Pattern.matches(patternStr, pass);
		boolean b2 = true;
		for (int i = 0; i < pass.length(); i++) {
			b2 = Pattern.matches("\\p{Punct}", ("" + pass.charAt(i)));
			if (b2) {
				b2 = false;
				break;
			}
		}

		if (b && !b2) {
			return true;
		}
		return false;
	}

	before(String time) : call (long Utility.restoreFormattedTime(String)) && args(time) {
		assert (isTimeIllegal(time));
	}

	before(String title) : call (void DetailedDiary.setTitle(String)) && args(title) {
		assert (title.length() <= 20);
	}

	before(String tag) : call (void DetailedDiary.setTag(String)) && args(tag) {
		assert (tag.length() <= 10);
	}

	before(String username, String password) : call ( boolean DiaryManager.register(String, String)) && args(username, password){
		assert (checkRegister(password));
	}

	before(String title, String tag) : call (void DiaryManager.loadDiaryList(String, String, long, long, SortType)) && args(title, tag, long, long, SortType) {
		if (title != null)
			assert (title.length() <= 20);
		if (tag != null)
			assert (tag.length() <= 20);
	}

}
