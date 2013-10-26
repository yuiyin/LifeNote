package main;

import data.DiaryManager;
import gui.StartFrame;

public class LifeNote {

	public static void main (String args[]) {
		//Welcome window
		StartFrame window = new StartFrame();
		window.setVisible(true);
		//Initialize data manager
		DiaryManager.getInstance().init(window);
	}
}
