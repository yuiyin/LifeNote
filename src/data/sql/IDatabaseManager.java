package data.sql;

import java.util.ArrayList;

import util.SortType;
import data.model.DetailedDiary;
import data.model.SimpleDiary;
import data.model.User;

public interface IDatabaseManager {

	public void init(String url, String user, String password);
	public User getUser(String username, String password);
	public User setUser(int id, String username, String password);
	public ArrayList<SimpleDiary> getDiaryList(String title, String tag, long startTime, long endTime, SortType sortType, String user);
	public DetailedDiary getDiary(int id);
	public void setDiary(DetailedDiary diary, boolean isDeletion);
	public void writeLog(String time, String type, String message);
}
