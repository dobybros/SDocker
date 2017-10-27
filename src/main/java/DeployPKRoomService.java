import com.docker.utils.DeployServiceUtils;

public class DeployPKRoomService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKRoomService";
        String dockerName = "docker";
        String serviceName = "pkroom";
        String gridfsHost = "mongodb://localhost:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
