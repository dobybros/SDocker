package task.utils;
import org.apache.zookeeper.CreateMode;

import task.data.Task;
import task.errors.CoreException;
import task.zk.WatcherExManager;

/**
 * Consumer queue
 */
public class ConsumerQueue<T extends Task> extends Queue<T>  {

	public ConsumerQueue(WatcherExManager watcherManager, String name) {
		super(watcherManager, name, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	
	@Override
	public String produce(T t) throws CoreException {
		throw new CoreException(CoreErrorCodes.ERROR_CORE_NOT_SUPPORTED, "Not supported");
	}
	
}