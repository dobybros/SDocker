package im;

import com.docker.utils.DeployServiceUtils;
import tuition.TuitionConstants;

public class DeployGetwayService {
    public static void main(String[] args) throws Exception {
        String servicePath = IMConstants.PATH + "GatewayService";
        String dockerName = "gateway";
        String serviceName = "gws";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "1";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
