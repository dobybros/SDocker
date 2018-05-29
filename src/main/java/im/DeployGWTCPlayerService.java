package im;

import com.docker.utils.DeployServiceUtils;

public class DeployGWTCPlayerService {
    public static void main(String[] args) throws Exception {
        String servicePath = IMConstants.PATH + "GWTCPlayerService";
        String dockerName = "gateway";
        String serviceName = "tcplayer";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "1";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
