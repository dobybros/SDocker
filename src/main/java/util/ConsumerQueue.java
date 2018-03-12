package zk;
import chat.errors.CoreException;
import org.apache.zookeeper.CreateMode;

/**
 * Consumer queue
 */
public class ConsumerQueue<T> extends Queue<T>  {
	public ConsumerQueue(WatcherExManager watcherManager, String name) {
		super(watcherManager, name, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	
	@Override
	public String produce(T t) throws CoreException {
//		throw new CoreException(CoreErrorCodes.ERROR_CORE_NOT_SUPPORTED, "Not supported");
		return "";
	}
	
}