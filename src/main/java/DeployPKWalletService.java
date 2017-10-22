import com.docker.utils.DeployServiceUtils;

public class DeployPKWalletService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKWalletService";
        String dockerName = "docker_beta";
        String serviceName = "pkwallet";
        String gridfsHost = "mongodb://localhost:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
