package data.event;

import java.util.Observable;

public abstract class Event extends Observable {
	
	public void changed(Object obj) {
        this.setChanged();
        this.notifyObservers(obj);
    }
}
