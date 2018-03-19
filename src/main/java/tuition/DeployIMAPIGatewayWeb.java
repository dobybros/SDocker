package tuition;

import com.docker.utils.DeployServiceUtils;

public class DeployIMAPIGatewayWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = TuitionConstants.PATH + "IMAPIGatewayWeb";
        String dockerName = TuitionConstants.DOCKERNAME;
        String serviceName = "acuim";
        String gridfsHost = TuitionConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TuitionConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
