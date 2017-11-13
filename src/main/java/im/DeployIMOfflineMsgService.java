package im;

import com.docker.utils.DeployServiceUtils;

public class DeployIMOfflineMsgService {
    public static void main(String[] args) throws Exception {
        String servicePath = IMConstants.PATH + "IMOfflineMsgService";
        String dockerName = "docker";
        String serviceName = "imofflinemessage";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "1";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
