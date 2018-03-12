package utils;

import chat.errors.ChatErrorCodes;
import chat.errors.CoreException;
import chat.logs.LoggerEx;
import chat.utils.BinarySerializable;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZookeeperPaths {
    private static final String TAG = "ZKP";

    public static void deletePath(ZooKeeper zk, String path) {
        try {
            zk.delete(path, 0);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
            LoggerEx.warn(TAG, "Delete ZK path " + path + " failed, " + e.getMessage());
        }
    }

    public static String ensurePath(ZooKeeper zk, String path, CreateMode createMode) throws CoreException {
        return ensurePath(zk, "", path, new byte[0], createMode);
    }

    public static <T extends BinarySerializable> String ensurePath(ZooKeeper zk, String prefixPath, String path, T t, CreateMode mode) throws CoreException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            t.persistent(baos);
            byte[] data = baos.toByteArray();
            return ensurePath(zk, prefixPath, path, data, mode);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CoreException(ChatErrorCodes.ERROR_CORE_ZKDATA_PERSISTENT_FAILED, "ZKData persistent failed, " + e.getMessage());
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String ensurePath(ZooKeeper zk, String prefixPath, String path, byte[] value, CreateMode mode) throws CoreException {
        if(path == null || StringUtils.isBlank(path))
            return prefixPath;
        Stat stat = null;
        String[] array = path.split("/");
        StringBuffer buffer = new StringBuffer(prefixPath);

        for (int i = 0; i < array.length; i++) {
            if(StringUtils.isBlank(array[i]))
                continue;
            buffer.append("/").append(array[i]);
            String p = buffer.toString();
//				stat = zk.exists(p, false);
//				if(stat == null) {
//					if(i < array.length - 1)
//						zk.create(p, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//					else
//						return zk.create(p, value, Ids.OPEN_ACL_UNSAFE, mode);
//				}
            try {
                if(i < array.length - 1) {
                    zk.create(p, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } else {
                    return zk.create(p, value, ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
                }
            } catch (KeeperException | InterruptedException e) {
                if(e instanceof KeeperException.NodeExistsException) {
                    if(i < array.length - 1)
                        continue;
                    else
                        return p;
                } else {
                    e.printStackTrace();
                    throw new CoreException(ChatErrorCodes.ERROR_CORE_ZKENSUREPATH_FAILED, new String[]{prefixPath, path},"Zookeeper ensure path " + prefixPath + " | " + path + " failed, " + e.getMessage());
                }
            }
        }
        return null;//Should never go here.

    }

    public static <T extends BinarySerializable> T getData(ZooKeeper zk, String path, Class<T> tClass) throws CoreException {
        byte[] data = null;
        try {
            data = zk.getData(path, false, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            if(e instanceof KeeperException.NoNodeException)
                return null;
            throw new CoreException(ChatErrorCodes.ERROR_CORE_ZKGETDATA_FAILED, "Zookeeper getData failed, " + e.getMessage());
        }
        try {
            T t = tClass.newInstance();
            if(data != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                t.resurrect(bais);
                bais.close();
            }
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CoreException(ChatErrorCodes.ERROR_CORE_ZKDATA_RESURRECT_FAILED, "ZKData resurrect failed, " + e.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new CoreException(ChatErrorCodes.ERROR_CORE_ZKDATA_NEWINSTANCE_FAILED, "ZKData new instance failed, " + e.getMessage());
        }
    }



}
