import com.docker.utils.DeployServiceUtils;

public class DeployIMUserInPresenceService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMUserInPresenceService";
        String dockerName = "docker";
        String serviceName = "imuserinpresence";
        String gridfsHost = "mongodb://localhost:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
