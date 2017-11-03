import com.docker.utils.DeployServiceUtils;

public class DeployPKZGMJService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKZGMahjongGame";
//        String servicePath = "/Users/admin/workSpace/PKUserService";
        String dockerName = "docker";
        String serviceName = "pkzgmj";
        String gridfsHost = "mongodb://localhost:7900";
//        String gridfsHost = "mongodb://poker.9spirit.cn:7900";
        String version = "1";
        String prefix = "scripts";
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
