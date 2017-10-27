import com.docker.utils.DeployServiceUtils;

public class DeployPKWalletService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKWalletService";
        String dockerName = "docker";
        String serviceName = "pkwallet";
        String gridfsHost = "mongodb://localhost:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
