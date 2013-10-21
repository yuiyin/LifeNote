package main;

import core.DiaryManager;
import gui.StartFrame;

public class LifeNote {

	public static void main (String args[]) {
		//Welcome window
		StartFrame window = new StartFrame();
		window.setVisible(true);
		//Initialize data manager
		DiaryManager.getInstance().init();
		DiaryManager.getInstance().addObserver(window);
		//Load diary data
		DiaryManager.getInstance().loadData();
	}
}
