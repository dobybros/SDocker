package app.sdocker.royal;

import com.docker.utils.DeployServiceUtils;

public class DeployMailService {
    public static void main(String[] args) throws Exception {
        String servicePath = RoyalWindowsConstants.PATH + "GmailService";
        String dockerName = RoyalWindowsConstants.DOCKERNAME;
        String serviceName = "gmail_test";
        String gridfsHost = RoyalWindowsConstants.GRIDFSHOST;
        String version = "1";
        String prefix = RoyalWindowsConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
