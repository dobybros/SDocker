package sso;

import com.docker.utils.DeployServiceUtils;
import onlinetution.OnlineTuitionConstants;

public class DeployOTSSOWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = SSOConstants.PATH + "SSOWeb";
        String dockerName = SSOConstants.DOCKERNAME;
        String serviceName = "acusso";
        String gridfsHost = SSOConstants.GRIDFSHOST;
        String version = "1";
        String prefix = SSOConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
