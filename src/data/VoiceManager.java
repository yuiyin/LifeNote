package data;

import aaa.PlayWav;
import aaa.RecordWav;

public class VoiceManager {

	private static VoiceManager instance = null;
	private RecordWav rw;
	private PlayWav pw;
	private boolean flag;
	
	private VoiceManager() {
		
	}
	
	public static VoiceManager getInstance() {
		if (instance == null) {
			instance = new VoiceManager();
		}
		return instance;
	}
	
	public void recordVoice(String url) {
		flag = true;
		rw = new RecordWav();
		rw.startRecord(url);
	}
	
	public void playVoice(String url) {
		flag = false;
		pw = new PlayWav(url);
		new Thread(pw).start();
	}
	
	public void stopVoice() {
		if (flag)
			rw.stopRecord();
		else
			pw.stop();
	}
}
