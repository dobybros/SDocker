package app.sdocker.royal;

import com.docker.utils.DeployServiceUtils;

public class IMAdminWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = RoyalWindowsConstants.PATH + "IMAdminWeb";
        String dockerName = RoyalWindowsConstants.DOCKERNAME;
        String serviceName = "imadminweb";
        String gridfsHost = RoyalWindowsConstants.GRIDFSHOST;
        String version = "1";
        String prefix = RoyalWindowsConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
