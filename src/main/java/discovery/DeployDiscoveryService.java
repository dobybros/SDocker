package discovery;

import com.docker.utils.DeployServiceUtils;

public class DeployDiscoveryService {
    public static void main(String[] args) throws Exception {
        String servicePath = DiscoveryConstants.PATH + "DiscoveryService";
        String dockerName = DiscoveryConstants.DOCKERNAME;
        String serviceName = "discovery";
        String gridfsHost = DiscoveryConstants.GRIDFSHOST;
        String version = "1";
        String prefix = DiscoveryConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
