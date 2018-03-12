package task.zk;

import java.util.HashSet;
import java.util.Set;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public abstract class LoopWatcher {
	private Set<EventType> eventTypes = null;
	public LoopWatcher(EventType... eventTypes) {
		if(eventTypes != null) {
			this.eventTypes = new HashSet<>();
			for(EventType eventType : eventTypes) 
				this.eventTypes.add(eventType);
		}
	}
	
	public boolean needInitiate() {return true;}
	
	public abstract boolean process(WatchedEvent event);
	
	public boolean uncaughtExeception(Throwable t) {return true;}
	
	public final void release() {
	}
	public Set<EventType> getEventTypes() {
		return eventTypes;
	}
	public void setEventTypes(Set<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}
}
