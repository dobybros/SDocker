package sso;

import com.docker.utils.DeployServiceUtils;

public class DeployUUMSService {
    public static void main(String[] args) throws Exception {
        String servicePath = SSOConstants.PATH + "UUMSService";
        String dockerName = SSOConstants.DOCKERNAME;
        String serviceName = "uums";
        String gridfsHost = SSOConstants.GRIDFSHOST;
        String version = "1";
        String prefix = SSOConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
