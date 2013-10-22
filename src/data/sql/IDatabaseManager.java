package data.sql;

import java.util.ArrayList;

import util.SortType;
import data.model.DetailedDiary;
import data.model.SimpleDiary;

public interface IDatabaseManager {

	public void init(String url, String user, String password);
	public ArrayList<SimpleDiary> getDiaryList(String title, String tag, long startTime, long endTime, SortType sortType);
	public DetailedDiary getDiary(int id);
	public void setDiary(DetailedDiary diary, boolean isDeletion);
}
