import com.docker.utils.DeployServiceUtils;

public class DeployIMApnService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMApnService";
        String dockerName = "docker";
        String serviceName = "imapn";
        String gridfsHost = "mongodb://localhost:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}