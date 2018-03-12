package zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

public class LockWatcherEx extends LoopWatcher {
	private Object lock;
	private boolean isProcessed = false;

	public LockWatcherEx(EventType...eventTypes) {
		this.lock = this;
	}
	
	public LockWatcherEx(Object lock, EventType...eventTypes) {
		super(eventTypes);
		this.lock = lock;
	}
	public LockWatcherEx() {
		this.lock = this;
	}
	public LockWatcherEx(Object lock) {
		this.lock = lock;
	}

	@Override
	public boolean process(WatchedEvent event) {
		synchronized (lock) {
			isProcessed = true;
			lock.notify();
		}
		return true;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

}