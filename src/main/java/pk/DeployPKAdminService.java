package pk;

import com.docker.utils.DeployServiceUtils;

public class DeployPKAdminService {
    public static void main(String[] args) throws Exception {
        String servicePath = PKConstants.PATH + "PKAdminService";
        String dockerName = PKConstants.DOCKERNAME;
        String serviceName = "admin";
        String gridfsHost = PKConstants.GRIDFSHOST;
        String version = "1";
        String prefix = PKConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
