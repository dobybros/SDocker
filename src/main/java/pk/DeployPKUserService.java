package pk;

import com.docker.utils.DeployServiceUtils;

public class DeployPKUserService {
    public static void main(String[] args) throws Exception {
        String servicePath = PKConstants.PATH + "PKUserService";
        String dockerName = PKConstants.DOCKERNAME;
        String serviceName = "pkuser";
        String gridfsHost = PKConstants.GRIDFSHOST;
//        String gridfsHost = "mongodb://poker.9spirit.cn:7900";
        String version = "1";
        String prefix = PKConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
