package zk;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import chat.errors.CoreException;
import chat.utils.IteratorEx;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

/**
 * Producer-Consumer queue
 */
public class Queue<T>  {
	private static final String TAG = Queue.class.getSimpleName();
	private static final String DEFAULT_ELEMENT_KEY = "e";
	/**
	 * Constructor of producer-consumer queue
	 * 
	 * @param address
	 * @param name
	 */
	private ZooKeeper zk;
	private Object mutex;
	private CreateMode createMode;
	private String root;
	private boolean isConnected = false;
	private LockWatcherEx watcher;
	private String elementKey;
	private WatcherExManager watcherManager;

	public Queue(WatcherExManager watcherManager, String name, CreateMode createMode) {
		// connect to server address
		mutex = new Integer[0];
		this.root = name;
		this.createMode = createMode;
		this.zk = watcherManager.getZk();
		this.watcherManager = watcherManager;
		this.elementKey = DEFAULT_ELEMENT_KEY;
		watcher = new LockWatcherEx(mutex, EventType.NodeChildrenChanged);
	}
	public Queue(WatcherExManager watcherManager, String name, String elementKey, CreateMode createMode) {
	    mutex = new Integer[0];
        this.root = name;
        this.createMode = createMode;
        this.zk = watcherManager.getZk();
        this.watcherManager = watcherManager;
        this.elementKey = elementKey;
        watcher = new LockWatcherEx(mutex, EventType.NodeChildrenChanged);
	}
	
	public void connect() throws CoreException {
		if(!isConnected) {
			try {
				if (zk.exists(root, false) == null) {

                }
//					throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUEPATH_NOT_EXIST, "Queue path doesn't exists.");
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
//				throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUEPATH_NOT_EXIST, "Queue path doesn't exists.");
			}
			watcherManager.addWatcherEx(root, watcher);
			isConnected = true;
		}
	}
	
	/**
	 * Add element to the queue.
	 * 
	 * @param
	 * @return 
	 * @return
	 * @throws CoreException 
	 */

	public String produce(T t) throws CoreException {
		try {
			return produce(this.elementKey, t);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
//			throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUE_ADDELEMENT_FAILED, "add element into queue failed, " + e.getMessage());
		}
		return "";
	}

	protected String produce(String key, T t) throws CoreException, KeeperException, InterruptedException {
		String path = "";
//		if(!isConnected)
//			connect();
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] data = null;
//		try {
//			t.persistent(baos);
//			data = baos.toByteArray();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUEELEMENT_OUTPUT_FAILED, "queue element output failed, " + e.getMessage());
//		} finally {
//			try {
//				baos.close();
//			} catch (IOException e) {
//			}
//		}
//		String path = zk.create(root + "/" + key, data, Ids.OPEN_ACL_UNSAFE,
//				createMode);
		//LoggerEx.debug(TAG, "Produced " + path + " value " + t);
		return path;
	}

	public interface NewInstanceHandler<T> {
		public T newInstance(InputStream is) throws IOException;
	}
	
	/*public class ClassNewInstance implements NewInstanceHandler<T>{
		private Class<T> tClass;
		public ClassNewInstance(Class<T> tClass) {
			this.tClass = tClass;
		}
		
		@Override
		public T newInstance(InputStream is) throws IOException {
			try {
				T t = tClass.newInstance();
				t.resurrect(is);
				return t;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
	}*/
	
	/**
	 * Remove first element from the queue.
	 * 
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	/*public T consume(Class<T> tClass) throws CoreException {
		return consume(new ClassNewInstance(tClass));
	}*/
	
    class SingleReturnIterator implements IteratorEx<T>{
        private T t;
        @Override
        public boolean iterate(T t) {
            this.t = t;
            return false;
        }
        T getT(){
            return t;
        }
    }
	
	public T consume() throws CoreException {
		SingleReturnIterator iterator = new SingleReturnIterator();
		consume(iterator);
		return iterator.getT();
	}
	
	public void consume(IteratorEx<T> iterator) throws CoreException {
		if(!isConnected)
			connect();
//		if(handler == null)
//			throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUE_CONSUME_T_NULL, "Consume t is null");
		while (isConnected) {
			synchronized (mutex) {
				List<String> list = null;
				try {
					list = zk.getChildren(root, false);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
//					throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUE_CONSUME_GETCHILDREN_FAILED, "Queue consume get children failed, " + e.getMessage());
				} 
				if (list == null || list.size() == 0) {
//					AcuLogger.debug(TAG, "Going to wait");
					try {
						mutex.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					Collections.sort(list);
//					String[] nodes = list.toArray(new String[list.size()]); 
//			        Arrays.sort(nodes);
					
					for(int i = 0; i < list.size(); i++) {
						byte[] data = null;
						try {
							data = zk.getData(root + "/" + list.get(i), false, null);
							zk.delete(root + "/" + list.get(i), 0);
						} catch(InterruptedException e) {
//							throw new CoreException(CoreErrorCodes.ERROR_CORE_QUEUE_CONSUME_INTERRUPTED, "Queue consume interrupted, " + e.getMessage());
						} catch(KeeperException e) {

						}
						if(data == null) {

                        }

					}
					return;
				}
			}
		}
	}
	
	public void disconnect() {
		isConnected = false;
		synchronized (mutex) {
			mutex.notifyAll();
		}
		watcherManager.deleteWatcherEx(root, watcher);
	}

	public CreateMode getCreateMode() {
		return createMode;
	}



	public void setCreateMode(CreateMode createMode) {
		this.createMode = createMode;
	}

	public WatcherExManager getWatcherManager() {
		return watcherManager;
	}

	public void setWatcherManager(WatcherExManager watcherManager) {
		this.watcherManager = watcherManager;
	}
    public String getElementKey() {
        return elementKey;
    }
    public void setElementKey(String elementKey) {
        this.elementKey = elementKey;
    }

}