import com.docker.utils.DeployServiceUtils;

public class DeployIMServerStatusService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMServerStatusService";
        String dockerName = "docker_beta";
        String serviceName = "imserverstatus";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
