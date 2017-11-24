package sscs;

import com.docker.utils.DeployServiceUtils;
import im.IMConstants;

public class DeploySSCSService {
    public static void main(String[] args) throws Exception {
        String servicePath = SSCSConstants.PATH + "SSCustomerStorageService";
        String dockerName = IMConstants.DOCKERNAME;
        String serviceName = "sscustomer";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "1";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
