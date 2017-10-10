import com.docker.utils.DeployServiceUtils;

import java.util.Arrays;

public class DeployDiscoveryService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/home/aplomb/dev/github/DiscoveryService";
        String dockerName = "docker_beta";
        String serviceName = "discovery";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
