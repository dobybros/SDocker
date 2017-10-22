import com.docker.utils.DeployServiceUtils;

public class DeployIMApnService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMApnService";
        String dockerName = "docker_beta";
        String serviceName = "imapn";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
