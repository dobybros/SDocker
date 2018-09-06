package royal;

import com.docker.utils.DeployServiceUtils;

public class DeployKMSMonitor {
    public static void main(String[] args) throws Exception {
        String servicePath = RoyalMacConstants.PATH + "KMSMonitorService";
        String dockerName = RoyalMacConstants.DOCKERNAME;
        String serviceName = "tckmsmonitor";
        String gridfsHost = RoyalMacConstants.GRIDFSHOST;
        String version = "1";
        String prefix = RoyalMacConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
