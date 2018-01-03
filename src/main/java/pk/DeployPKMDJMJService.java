package pk;

import com.docker.utils.DeployServiceUtils;

public class DeployPKMDJMJService {
    public static void main(String[] args) throws Exception {
        String servicePath = PKConstants.PATH + "PKMDJMahjongGame";
        String dockerName = "gateway";
        String serviceName = "pkmdj";
        String gridfsHost = PKConstants.GRIDFSHOST;
//        String gridfsHost = "mongodb://poker.9spirit.cn:7900";
        String version = "1";
        String prefix = PKConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
