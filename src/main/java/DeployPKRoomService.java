import com.docker.utils.DeployServiceUtils;

public class DeployPKRoomService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKRoomService";
        String dockerName = "docker_beta";
        String serviceName = "pkroom";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
