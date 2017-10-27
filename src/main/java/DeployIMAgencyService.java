import com.docker.utils.DeployServiceUtils;

public class DeployIMAgencyService {
    public static void main(String[] args) throws Exception {
        String servicePath = "/Users/admin/workSpace/manageservers/IMAgencyService";
        String dockerName = "gateway";
        String serviceName = "imagency";
        String gridfsHost = "mongodb://localhost:7900";
//        String gridfsHost = "mongodb://poker.9spirit.cn:7900";
        String version = "1";
        DeployServiceUtils.main(new String[]{"-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
