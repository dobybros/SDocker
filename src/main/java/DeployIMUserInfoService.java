import com.docker.utils.DeployServiceUtils;

public class DeployIMUserInfoService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/SDockerService/IMUserInfoService";
        String dockerName = "docker_beta";
        String serviceName = "imuserinfo";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
