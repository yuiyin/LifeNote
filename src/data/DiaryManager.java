package data;

import java.util.ArrayList;
import java.util.Observer;

import util.SortType;
import util.Utility;
import data.event.TaskFinishEvent;
import data.event.UpdateProgressEvent;
import data.model.DetailedDiary;
import data.model.SimpleDiary;
import data.model.User;
import data.sql.IDatabaseManager;
import data.sql.SQLiteManager;

//Data processor
public class DiaryManager {

	private static DiaryManager instance = null;
	private ArrayList<SimpleDiary> diaryList = null;
	private DetailedDiary detailedDiary = null;
	private IDatabaseManager dbManager = null;
	private ProgressUpdater progressUpdater = null;
	private User currentUser = null;
	
	private DiaryManager() {
		
	}
	
	public static DiaryManager getInstance() {
		if (instance == null) {
			instance = new DiaryManager();
		}
		return instance;
	}
	
	//Initialize
	public void init(Observer observer) {
		dbManager = new SQLiteManager();
		progressUpdater = new ProgressUpdater();
		progressUpdater.addObserver(observer);
		
		Thread t = new Thread(progressUpdater);
		t.start();
		
		dbManager.init(Utility.SQL_URL, Utility.SQL_USER, Utility.SQL_PASSWORD);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		progressUpdater.stop();
	}
	
	public boolean signin(String username, String password) {
		currentUser = dbManager.getUser(username, password);
		if (currentUser == null) {
			return false;
		} else
			return true;
	}
	
	public boolean register(String username, String password) {
		currentUser = dbManager.setUser(Utility.generateRandomID(), username, password);
		if (currentUser == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public User getUser() {
		return currentUser;
	}
	
	public void addObserver(Observer observer) {
		progressUpdater.addObserver(observer);
	}
	
	public void loadDiaryList() {
		loadDiaryList(null, null, -1, -1, SortType.SORT_BY_TIME);
	}
	
	public void loadDiaryList(String title, String tag, long startTime, long endTime, SortType sortType) {
		diaryList = dbManager.getDiaryList(title, tag, startTime, endTime, sortType, currentUser.getUsername());
	}
	
	public ArrayList<SimpleDiary> getDiaryList() {
		return diaryList;
	}
	
	public void loadDiary(int id) {
		detailedDiary = dbManager.getDiary(id);
	}
	
	public DetailedDiary getDiary() {
		return detailedDiary;
	}
	
	public void deleteDiary(DetailedDiary dd) {
		dbManager.setDiary(dd, true);
	}
	
	public void saveDiary(DetailedDiary dd) {
		detailedDiary = dd;
		dbManager.setDiary(dd, false);
	}
	
	public void saveCreate(DetailedDiary dd) {
		saveDiary(dd);
	}
	
	public void saveComment(DetailedDiary dd) {
		saveDiary(dd);
	}
}

//Start a thread to update progress bar
class ProgressUpdater implements Runnable{

	private boolean flag = true;
	private UpdateProgressEvent updateProgressEvent = new UpdateProgressEvent();
	private TaskFinishEvent taskFinishEvent = new TaskFinishEvent();
	
	@Override
	public void run() {
		while (flag) {
			updateProgressEvent.changed(null);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		taskFinishEvent.changed(null);
	}
	
	public void stop() {
		flag = false;
	}
	
	public void addObserver(Observer observer) {
		updateProgressEvent.addObserver(observer);
		taskFinishEvent.addObserver(observer);
	}
}
