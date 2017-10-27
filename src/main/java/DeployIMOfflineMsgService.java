import com.docker.utils.DeployServiceUtils;

public class DeployIMOfflineMsgService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMOfflineMsgService";
        String dockerName = "docker";
        String serviceName = "imofflinemessage";
        String gridfsHost = "mongodb://localhost:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
