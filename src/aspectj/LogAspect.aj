package aspectj;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.model.DetailedDiary;
import data.sql.IDatabaseManager;
import data.sql.SQLiteManager;
import data.DiaryManager;

public aspect LogAspect {
	private IDatabaseManager dbm = new SQLiteManager();

	private final String DIARY_LOG = "diary";
	private final String COMMENT_LOG = "comment";
	private final String EXCEPTION_LOG = "exception";

	private String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	pointcut allCall(): call(public Object *(..)) || call(private Object *(..))|| call(protected Object *(..));

	before(DetailedDiary diary) : call (void DiaryManager.saveCreate(DetailedDiary)) && args(diary) {
		StringBuilder builder = new StringBuilder();
		builder.append("用户").append(diary.getUsername()).append("增加日志：")
				.append(diary.getId()).append("-").append(diary.getTitle());
		dbm.writeLog(getCurrentTime(), DIARY_LOG, builder.toString());

	}

	before(DetailedDiary diary) : call (void DiaryManager.saveUpdate(DetailedDiary)) && args(diary) {
		StringBuilder builder = new StringBuilder();
		builder.append("用户").append(diary.getUsername()).append("修改日志：")
				.append(diary.getId()).append("-").append(diary.getTitle());
		dbm.writeLog(getCurrentTime(), DIARY_LOG, builder.toString());
	}

	before(DetailedDiary diary) : call (void DiaryManager.saveComment(DetailedDiary)) && args(diary) {
		String comment = diary.getComment();
		int index = comment.lastIndexOf("\n");
		if (index == -1)
			index = 0;
		String lastComment = comment.substring(index, comment.length());
		StringBuilder builder = new StringBuilder();
		builder.append("用户").append(DiaryManager.getInstance().getUser().getUsername()).append("评论用户")
				.append(diary.getUsername()).append("的日志：")
				.append(diary.getTitle()).append("。内容为：").append(lastComment);
		dbm.writeLog(getCurrentTime(), COMMENT_LOG, builder.toString());
	}

	before(DetailedDiary diary) : call (void DiaryManager.deleteDiary(DetailedDiary)) && args(diary) {
		StringBuilder builder = new StringBuilder();
		builder.append("用户").append(diary.getUsername()).append("删除日志：")
				.append(diary.getId()).append("-").append(diary.getTitle());
		dbm.writeLog(getCurrentTime(), DIARY_LOG, builder.toString());
	}
	
	after() throwing (Exception e): allCall() {
		dbm.writeLog(getCurrentTime(), EXCEPTION_LOG, e.getMessage());
	}

}
