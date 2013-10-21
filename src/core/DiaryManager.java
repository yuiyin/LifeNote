package core;

import java.util.ArrayList;
import java.util.Observer;

import sql.IDatabaseManager;
import sql.SQLiteManager;
import util.SortType;
import util.Utility;
import data.DetailedDiary;
import data.SimpleDiary;
import event.TaskFinishEvent;
import event.UpdateProgressEvent;

//Data processor
public class DiaryManager {

	private static DiaryManager instance = null;
	private ArrayList<SimpleDiary> diaryList = null;
	private DetailedDiary detailedDiary = null;
	private IDatabaseManager dbManager = null;
	private ProgressUpdater progressUpdater = null;
	
	private DiaryManager() {
		
	}
	
	public static DiaryManager getInstance() {
		if (instance == null) {
			instance = new DiaryManager();
		}
		return instance;
	}
	
	//Initialize
	public void init() {
		diaryList = new ArrayList<SimpleDiary>();
		dbManager = new SQLiteManager();
		dbManager.init(Utility.SQL_URL, Utility.SQL_USER, Utility.SQL_PASSWORD);
		progressUpdater = new ProgressUpdater();
	}
	
	//Load data from database and get list of simple information of diary
	public void loadData() {
		//Start a thread to update progress bar
		Thread t = new Thread(progressUpdater);
		t.start();
		
		diaryList = dbManager.getDiaryList(null, null, -1, -1, SortType.SORT_BY_TIME);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Stop the thread updating progress bar
		progressUpdater.stop();
	}
	
	public void addObserver(Observer observer) {
		progressUpdater.addObserver(observer);
	}
	
	public void loadDiaryList() {
		loadDiaryList(null, null, -1, -1, SortType.SORT_BY_TIME);
	}
	
	public void loadDiaryList(String title, String tag, long startTime, long endTime, SortType sortType) {
		diaryList = dbManager.getDiaryList(title, tag, startTime, endTime, sortType);
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
