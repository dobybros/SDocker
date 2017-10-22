import com.docker.utils.DeployServiceUtils;

public class DeployPKUserService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/PKUserService";
//        String servicePath = "/Users/admin/workSpace/PKUserService";
        String dockerName = "docker_beta";
        String serviceName = "pkuser";
//        String gridfsHost = "mongodb://localhost:7900";
        String gridfsHost = "mongodb://poker.9spirit.cn:7900";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
