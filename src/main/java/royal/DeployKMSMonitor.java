package royal;

import com.docker.utils.DeployServiceUtils;

public class DeployKMSMonitor {
    public static void main(String[] args) throws Exception {
        String servicePath = RoyalWindowsConstants.PATH + "KMSMonitorService";
        String dockerName = RoyalWindowsConstants.DOCKERNAME;
        String serviceName = "tckmsmonitor";
        String gridfsHost = RoyalWindowsConstants.GRIDFSHOST;
        String version = "1";
        String prefix = RoyalWindowsConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
